package com.mbs.workoutplanner.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mbs.workoutplanner.application.Application_Constants
import com.mbs.workoutplanner.models.UserDataModel
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserDataRepository @Inject constructor() : UserDataInterface {
    private val db by lazy {
        Firebase.firestore
    }
    private val currentUserUid by lazy {
        Firebase.auth.currentUser?.uid
    }

    override suspend fun registerUserData(user: UserDataModel) =
        suspendCoroutine<Unit> { continuation ->
            currentUserUid?.let {
                db.collection(Application_Constants.USER_DATA_COLLECTION)
                    .document(it)
                    .set(user.toMap())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(Unit)
                        } else {
                            task.exception?.let { exception ->
                                continuation.resumeWithException(exception)
                            }
                        }
                    }
            }
        }


    override suspend fun retrieveUserData(): UserDataModel? {
        return suspendCoroutine { continuation ->
            currentUserUid?.let {
                db.collection(Application_Constants.USER_DATA_COLLECTION).document(it)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val userData = UserDataModel(
                                document.getString("name") ?: "",
                                document.getString("modality") ?: "",
                                document.getDouble("weight") ?: 0.0,
                                document.getLong("height") ?: 0,
                                document.getDouble("bf") ?: 0.0
                            )
                            continuation.resume(userData)
                        } else {
                            continuation.resume(null)
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Tratar o erro de acordo com a necessidade do aplicativo
                        continuation.resumeWithException(exception)
                    }
            }
        }
    }
}