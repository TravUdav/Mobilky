package ru.mirea.andreevapk.data.mock

class MockFirebaseUser(var uid: String, var displayName: String?)

class MockFirebaseAuth {
    private var currentUser: MockFirebaseUser? = MockFirebaseUser("mockUid123", "John Doe")

    fun getCurrentUser(): MockFirebaseUser? = currentUser

    fun signOut() {
        currentUser = null
    }

    fun updateCurrentUserName(newName: String): Boolean {
        currentUser?.displayName = newName
        return true
    }

    fun signIn(email: String, password: String): Boolean {
        //todo smth
        return true
    }
}