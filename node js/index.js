const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


exports.complaintNotification = functions.database.ref('/notifications/complaints/{head_id}/{notification_id}')
    .onWrite((snapshot, context) => {
      // Grab the current value of what was written to the Realtime Database.
     
      const head_id = context.params.head_id; 
      const notification = context.params.notification_id;
     
   	 console.log('Worker Head id ',head_id);
  	 console.log('push id ', notification);

  if (!snapshot.after.val()) {
            console.log('no fcm found')
            return
        }
      

// get the sender uid 
   const fromUser = admin.database().ref(`/notifications/complaints/${head_id}/${notification}`).once('value');

    return fromUser.then(fromUserResult => {

      const from_user_id = fromUserResult.val().from;
       console.log('From user id ',from_user_id);


     // query to get the user name and token 
      const userQuery = admin.database().ref(`Users/${from_user_id}/user_name`).once('value');
      const tokenQuery = admin.database().ref(`WorkersHead/${head_id}/token`).once('value');
      
      return Promise.all([userQuery , tokenQuery]).then(result => {

       const userName = result[0].val();
       const receiverToken = result[1].val();

      const payLoad = {
      	 notification: {
            title: 'Complaint',
            body: `${userName} add a new complaint`,
            sound: 'default'
      	 }
      };

        return admin.messaging().sendToDevice(receiverToken , payLoad).then (response => {
        	return console.log('Notification has been sent')
        	 
        });// send notification brackets


      }); // promise all brackets

    }); // from user query brackets

       
   }); // complaint notification brackets





// mehtod to send the notification when complaint is completed
exports.complaintCompleted = functions.database.ref('/notifications/completedWork/{user_id}/{notification_id}')
    .onWrite((snapshot, context) => {
     
      const user_id = context.params.user_id; 
      const notification = context.params.notification_id;
     
   	 console.log('User id ',user_id);
  	 console.log('push id ', notification);

  if (!snapshot.after.val()) {
            console.log('no fcm found')
            return
        }


//  device token ref
const tokens = admin.database().ref(`/Users/${user_id}/device_token`).once('value');

    return tokens.then(result => {
      
          const token = result.val();

        const payload = {
            notification: {
                title: 'Congraulation',
                body: "You complaint has completed",
                sound: 'default',
                click_action: "com.uzair.kptehsilmunicipaladministrationservices_TARGET_NOTIFICATION"
            }
        };

        
        return admin.messaging().sendToDevice(token, payload);

    }); // tokens query brackets

    });// completedComplaint method brackets




// method to get notification when user add rating

exports.ratingNotification = functions.database.ref('/notifications/rating/{head_id}/{notification_id}')
    .onWrite((snapshot, context) => {
      // Grab the current value of what was written to the Realtime Database.
     
      const head_id = context.params.head_id; 
      const notification = context.params.notification_id;
     
   	 console.log('Worker Head id ',head_id);
  	 console.log('push id ', notification);

  if (!snapshot.after.val()) {
            console.log('no fcm found')
            return
        }
      
// ref to get the sender
   const fromUser = admin.database().ref(`/notifications/rating/${head_id}/${notification}`).once('value');

    return fromUser.then(fromUserResult => {

      const from_user_id = fromUserResult.val().from;
       console.log('From user id ',from_user_id);


     // query to get the user name and token 
      const userQuery = admin.database().ref(`Users/${from_user_id}/user_name`).once('value');
      const tokenQuery = admin.database().ref(`WorkersHead/${head_id}/token`).once('value');
      
      return Promise.all([userQuery , tokenQuery]).then(result => {

       const userName = result[0].val();
       const receiverToken = result[1].val();

      const payLoad = {
      	 notification: {
            title: 'Complaint Rating',
            body: `${userName} left a feedback on your completed work`,
            sound: 'default'
      	 }
      };

        return admin.messaging().sendToDevice(receiverToken , payLoad).then (response => {
        	return console.log('Notification has been sent')
        	 
        });// send notification brackets


      }); // promise all brackets

    }); // from user query brackets

       
   }); // complaint notification brackets






    // method to get notification when user request fire fighting

exports.fireFightingNotification = functions.database.ref('/notifications/fire_fighting/{head_id}/{notification_id}')
    .onWrite((snapshot, context) => {
      // Grab the current value of what was written to the Realtime Database.
     
      const head_id = context.params.head_id; 
      const notification = context.params.notification_id;
     
   	 console.log('Worker Head id ',head_id);
  	 console.log('push id ', notification);

  if (!snapshot.after.val()) {
            console.log('no fcm found')
            return
        }
      
// ref to get the sender
   const fromUser = admin.database().ref(`/notifications/fire_fighting/${head_id}/${notification}`).once('value');

    return fromUser.then(fromUserResult => {

      const from_user_id = fromUserResult.val().from;
       console.log('From user id ',from_user_id);


     // query to get the user name and token 
      const userQuery = admin.database().ref(`Users/${from_user_id}/user_name`).once('value');
      const tokenQuery = admin.database().ref(`WorkersHead/${head_id}/token`).once('value');
      
      return Promise.all([userQuery , tokenQuery]).then(result => {

       const userName = result[0].val();
       const receiverToken = result[1].val();

      const payLoad = {
      	 notification: {
            title: 'Fire Fighting',
            body: `${userName} request for fire Fighting service`,
            sound: 'default'
      	 }
      };

        return admin.messaging().sendToDevice(receiverToken , payLoad).then (response => {
        	return console.log('Notification has been sent')
        	 
        });// send notification brackets


      }); // promise all brackets

    }); // from user query brackets

       
   }); // complaint notification brackets