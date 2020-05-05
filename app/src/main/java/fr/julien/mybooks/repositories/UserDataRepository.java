package fr.julien.mybooks.repositories;

import androidx.lifecycle.LiveData;
import fr.julien.mybooks.database.UserDao;
import fr.julien.mybooks.models.User;

public class UserDataRepository {

    private final UserDao userDao;
    public static long USER_ID = 1;

    public UserDataRepository(UserDao userDao) { this.userDao = userDao; }

    public LiveData<User> getUser(long userId) { return this.userDao.getUser(userId); }

    public void createUser(User user){this.userDao.createUser(user);}
}
