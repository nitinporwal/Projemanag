package com.example.projemanage.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.projemanage.activities.*
import com.example.projemanage.models.Board
import com.example.projemanage.models.User
import com.example.projemanage.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserId())
            .set(userInfo, SetOptions.merge()).addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error writing document", e)
            }
    }

    fun createBoard(activity: CreateBoardActivity, boardInfo: Board) {
        mFireStore.collection(Constants.BOARD).document().set(boardInfo, SetOptions.merge())
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Board Crated Successfully!")
                Toast.makeText(activity, "Board Crated Successfully!", Toast.LENGTH_SHORT).show()
                activity.boardCreatedSuccessfully()
            }.addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error writing document", e)
            }
    }

    fun getCurrentUserId(): String {

        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun updateUserProfileData(activity: ProfileActivity, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).update(userHashMap)
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Profile Data Updated")
                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_LONG).show()
                activity.profileUpdateSuccess()
            }.addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board")
                Toast.makeText(activity, "Error when updating the profile!", Toast.LENGTH_LONG)
                    .show()
            }
    }

    fun loadUserData(activity: Activity) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)

                when (activity) {
                    is SignInActivity -> {
                        if (loggedInUser != null) {
                            activity.signInSuccess(loggedInUser)
                        }
                    }
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedInUser)
                    }
                    is ProfileActivity -> {
                        activity.setUserDataInUI(loggedInUser)
                    }
                }
            }.addOnFailureListener { e ->
                when (activity) {
                    is SignInActivity -> {
                        activity.hideProgressDialog()
                    }
                    is MainActivity -> {
                        activity.hideProgressDialog()
                    }
                    is ProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e(activity.javaClass.simpleName, "Error in registering user", e)
            }
    }
}