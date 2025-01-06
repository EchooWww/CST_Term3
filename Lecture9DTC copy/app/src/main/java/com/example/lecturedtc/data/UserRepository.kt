package com.example.lecturedtc.data

// One repository for each entity(model) in the app
class UserRepository(private val userDao: UserDao) {
    // any business logic here

    fun insertEntity(user: LocalUser) {
        userDao.add(user)
    }

    fun getAllEntities(): List<LocalUser> {
        return userDao.getAll()
    }

    fun deleteEntity(user: LocalUser) {
        userDao.delete(user)
    }
}