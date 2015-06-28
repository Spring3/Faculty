import com.kpi.faculty.dao.CourseDAO;
import com.kpi.faculty.dao.HumanDAO;
import com.kpi.faculty.models.Course;
import com.kpi.faculty.models.Human;
import com.kpi.faculty.models.Student;
import com.kpi.faculty.models.Teacher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class HumanDAOTest {

    private CourseDAO dao;
    private HumanDAO humanDAO;
    private Course tempCourse;
    private Human student;

    @Before
    public void init() throws SQLException {
        dao = new CourseDAO();
        humanDAO = new HumanDAO();
        student = new Student("Student4Test", "student", "Johny", "Doe");
        Human teacher = new Teacher("username", "password", "John", "Doe");
        tempCourse = new Course("Test course", teacher);
    }

    @Test
    public void getAllTest(){
        int size = humanDAO.getAll().size();
        Assert.assertEquals(humanDAO.getAll().size(), size);
        Assert.assertTrue(humanDAO.create(student));
        Assert.assertEquals(humanDAO.getAll().size(), size + 1);
        student = humanDAO.get(student.getUsername());
        Assert.assertTrue(humanDAO.remove(student));
    }

    @Test
    public void getAllStudentsForTest() {
        Assert.assertEquals(humanDAO.getAllStudentsFor(tempCourse).size(), 0);
    }

    @Test
    public void  getTest(){
        Assert.assertEquals(humanDAO.get(student.getUsername()), null);
        Assert.assertTrue(humanDAO.create(student));
        Assert.assertNotEquals(humanDAO.get(student.getUsername()).getId(), 0);
        student = humanDAO.get(student.getUsername());
        Assert.assertTrue(humanDAO.remove(student));
    }

    @Test
    public void createTest(){
        Assert.assertEquals(humanDAO.get(student.getUsername()), null);
        Assert.assertTrue(humanDAO.create(student));
        Assert.assertNotNull(humanDAO.get(student.getUsername()));
        Assert.assertNotEquals(humanDAO.get(student.getUsername()).getId(), 0);
        student = humanDAO.get(student.getUsername());
        Assert.assertTrue(humanDAO.remove(student));
    }

    @Test
    public void removeTest() {
        Assert.assertTrue(humanDAO.remove(student));
        Assert.assertTrue(humanDAO.create(student));
        student = humanDAO.get(student.getUsername());
        Assert.assertTrue(humanDAO.remove(student));
        Assert.assertTrue(humanDAO.remove(student));
    }

    @Test  //
    public void updateTest(){
        Assert.assertTrue(humanDAO.create(student));
        student = humanDAO.get(student.getUsername());
        student.setName("Alice");
        Assert.assertTrue(humanDAO.update(student));
        Assert.assertEquals(humanDAO.get(student.getUsername()).getName(), "Alice");
        Assert.assertTrue(humanDAO.remove(student));
    }
}
