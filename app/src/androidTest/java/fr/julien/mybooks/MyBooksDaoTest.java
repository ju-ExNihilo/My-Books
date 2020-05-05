package fr.julien.mybooks;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import fr.julien.mybooks.database.MyBooksDatabase;
import fr.julien.mybooks.models.MyBooks;
import fr.julien.mybooks.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MyBooksDaoTest {

    // FOR DATA
    private MyBooksDatabase database;
    // DATA SET FOR TEST
    private static long USER_ID = 1;
    private static User USER_DEMO = new User(USER_ID, "Julien", "https://i.pravatar.cc/150?u=a042581f4e29026704f");
    private static MyBooks MY_BOOKS_DEMO = new MyBooks("16","my book","julien","","fr","free","julien.fr",
            "julien.fr","this is my book",false,true,USER_ID);
    private static MyBooks MY_BOOKS_DEMO_1 = new MyBooks("17","my book1","julien","","fr","free","julien.fr",
            "julien.fr","this is my book1",false,true,USER_ID);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                MyBooksDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetBook() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.userDao().createUser(USER_DEMO);
        this.database.myBooksDao().insertBook(MY_BOOKS_DEMO);
        this.database.myBooksDao().insertBook(MY_BOOKS_DEMO_1);
        // TEST
        List<MyBooks> books = LiveDataTestUtil.getValue(this.database.myBooksDao().getReadListBooks(true));
        assertEquals(2, books.size());
    }
    @Test
    public void insertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.userDao().createUser(USER_DEMO);
        // TEST
        User user = LiveDataTestUtil.getValue(this.database.userDao().getUser(USER_ID));
        assertTrue(user.getUsername().equals(USER_DEMO.getUsername()) && user.getId() == USER_ID);
    }
}
