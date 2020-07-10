package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ModelOfComplaintRecycler.ModelForComplaintFeedback;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.UserFeedBackPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.UserFeedBackView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFeedBackPresenterImplementer implements UserFeedBackPresenter
{
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    private UserFeedBackView feedBackView;
    private String pushKey;
    private double total;
    private int counter = 0;

    public UserFeedBackPresenterImplementer(UserFeedBackView feedBackView)
    {
        this.feedBackView = feedBackView;
    }


    @Override
    public void getCompletedWorkDetails(DatabaseReference dbRef , String complaintKey)
    {
        dbRef.child(complaintKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // get feedback work data
                ModelForComplaintFeedback model = dataSnapshot.getValue(ModelForComplaintFeedback.class);

                // send data to textviews
                feedBackView.onSetDataInTextViews(model.getTitle(), model.getFirstWorker(),
                        model.getSecondWorker(), model.getImageUrl(),model.getCompletedDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void addFirstWorkerRating(final DatabaseReference databaseRef, final String rating, final String comment,
                                     String firstWorkerName, final String uid,
                                     final Uri imageUrl, final String complaintKey, String complaintType)
    {
         getWorkerIdAndStoreRating(databaseRef ,rating , comment , firstWorkerName , uid ,
                 imageUrl , complaintKey , complaintType);

    }

    @Override
    public void addSecondWorkerRating(DatabaseReference databaseRef, String rating, String comment,
                                      String secondWorkerName, String uid,
                                      Uri imageUrl, String complaintKey,String complaintType)
    {
        getWorkerIdAndStoreRating(databaseRef ,rating , comment , secondWorkerName ,
                uid , imageUrl , complaintKey , complaintType);

    }




    // get worker id and store rating
    private void getWorkerIdAndStoreRating(DatabaseReference dbRef, final String rating,
                                           final String comment, String workerName, final String uid,
                                           final Uri imageUrl, final String complaintKey , final String complaintType)
    {

        if(dbRef!= null && !comment.isEmpty() && !workerName.isEmpty() && imageUrl != null)
        {
            feedBackView.showProgressBar();


            try {

                // get the worker id from worker list through Worker Name
                Query query = dbRef.child("Worker List").orderByChild("nameOfWorker").equalTo(workerName);

                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                    {
                        // get the worker key and pass to the rating method to add rating to
                        // the worker who done the work
                        pushKey = dataSnapshot.child("pushKey").getValue().toString();
                        addRatingIntoDatabase(rating, comment, imageUrl, uid, pushKey, complaintKey , complaintType);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
            catch (Exception e)
            {
                feedBackView.showMessage(e.getMessage());
                feedBackView.hideProgressBar();
            }
        }
        else
        {
            feedBackView.showMessage("Please rating, feedback image and comment is require");
        }

    }


    // add  worker rating method
    private void addRatingIntoDatabase(final String rating , final String comment , final Uri image ,
                                       final String uid , final String pushKey ,
                                       final String complaintKey , final String complaintType)
    {
        // store rating data in firebase
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Ratings");
            final StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                    .child("RatingImages").child(image.getLastPathSegment());

            // store image
            imageRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // get download url of image
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // store data into firebase database
                            Map<String, Object> ratingData = new HashMap();
                            ratingData.put("user_rating", rating);
                            ratingData.put("comment", comment);
                            ratingData.put("image", uri.toString());
                            ratingData.put("user_id", uid);
                            ratingData.put("worker_id", pushKey);
                            ratingData.put("complaint_key", complaintKey);

                            databaseReference.push().setValue(ratingData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        /*
                                        call update average method to update the average rating of workers
                                        and add notification data method to send notification
                                         */
                                        updateAverageRating(pushKey);
                                        addNotificationData(uid , complaintType);
                                        feedBackView.showMessage("Successfully added");
                                        feedBackView.hideProgressBar();
                                        // call validation method
                                        addRatingValidation(uid , complaintKey);
                                    }
                                }
                            });
                        }
                    });
                }
            }); }


            // send notification data to firebase
    private void addNotificationData(final String uid, String complaintType)
    {
        // first get the uid of department head
        mDatabaseRef.child("WorkersHead").orderByChild("department").equalTo(complaintType)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                    {
                        String workerHeadUid = dataSnapshot.child("uid").getValue().toString();
                        // store uid for notification
                        Map<String , String> notificationData = new HashMap<>();
                        notificationData.put("from", uid);

                        mDatabaseRef.child("notifications").child("rating")
                                .child(workerHeadUid).push().setValue(notificationData);
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

    }

    // update the average rating of workers
    private void updateAverageRating(final String key)
    {
        // get the worker id from rating node
                 DatabaseReference ratingDb  = FirebaseDatabase.getInstance().getReference().child("Ratings");
                        Query selectQuery =  ratingDb.orderByChild("worker_id").equalTo(key);

                        selectQuery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                // get the all ratings of worker who complete the work
                                for(DataSnapshot workerRating : dataSnapshot.getChildren())
                                {
                                    double rating = Double.parseDouble(workerRating.child("user_rating").getValue().toString());
                                    total = total + rating;
                                    counter = counter + 1;
                                }

                                Log.d("totalRating", "onChildAdded: "+total/counter);

                                // update the average rating of workers who complete the worker
                                DatabaseReference updateRating  = FirebaseDatabase.getInstance().getReference().child("Worker List");

                                Map rating  = new HashMap<>();
                                rating.put("average_rating",String.valueOf(total/counter));
                                rating.put("total_reviews",String.valueOf(counter));
                                updateRating.child(key).updateChildren(rating);


                            }

                                    @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {}});
    }


    // add complaint validation means user can add rating only once
    private void addRatingValidation(String uid , String complainKey)
    {
        if(!uid.isEmpty())
        {
            Map<String , String> validationData = new HashMap();
            validationData.put(complainKey , "true");

            mDatabaseRef.child("RatingValidation").child(uid).child(complainKey).setValue(validationData);

        }

    }

}
