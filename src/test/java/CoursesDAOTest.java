import com.kpi.faculty.dao.CourseDAO;
import com.kpi.faculty.dao.HumanDAO;
import com.kpi.faculty.models.Course;
import com.kpi.faculty.models.Human;
import com.kpi.faculty.models.Student;
import com.kpi.faculty.models.Teacher;
import com.kpi.faculty.util.ConnectionPool;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class CoursesDAOTest {

    private CourseDAO dao;
    private HumanDAO humanDAO;
    private Course tempCourse;
    private Human student;
    private Human teacher;

    @Before
    public void init() throws SQLException {
        dao = new CourseDAO();
        humanDAO = new HumanDAO();
        student = new Student("Student4Test", "student", "Johny", "Doe");
        teacher = new Teacher("username", "password", "John", "Doe");
        tempCourse = new Course("Test course", teacher);
    }

    @Test
    public void getAllTest(){
        int size = dao.getAll().size();
        Assert.assertEquals(dao.getAll().size(), size);
        Assert.assertTrue(dao.create(tempCourse));
        Assert.assertEquals(dao.getAll().size(), size + 1);
        tempCourse = dao.get(tempCourse.getName());
        Assert.assertTrue(dao.remove(tempCourse));
        Assert.assertEquals(dao.getAll().size(), size);
    }

    @Test
    public void collectFeedbackAndMarksForTest(){
        Assert.assertEquals(dao.collectFeedbackAndMarksFor(student).size(), 0);
    }

    @Test
    public void getAllCoursesOfTest(){
        Assert.assertEquals(dao.getAllCoursesOf(teacher).size(), 0);
    }

    @Test
    public void getAllCoursesForTest(){
        int size = dao.getAll().size() - dao.getAllAvailableCoursesFor(student).size();
        Assert.assertEquals(dao.getAllCoursesFor(student).size(), size);
    }

    @Test
    public void getAllAvailableCoursesForTest(){
        int size = dao.getAll().size() - dao.getAllCoursesFor(student).size();
        Assert.assertEquals(dao.getAllAvailableCoursesFor(student).size(), size);
    }

    @Test
    public void getFeedbackForStudentTest(){
        Assert.assertNull(dao.getFeedBackForStudent(student, tempCourse, null));
    }

    @Test
    public void getMarkForStudentTest(){
        Assert.assertNull(dao.getFeedBackForStudent(student, tempCourse, null));
    }

    @Test
    public void getTest(){
        Assert.assertTrue(dao.create(tempCourse));
        Assert.assertNotNull(dao.get(tempCourse.getName()));
        Assert.assertNotEquals(dao.get(tempCourse.getName()).getId(), 0);
        tempCourse = dao.get(tempCourse.getName());
        Assert.assertEquals(tempCourse, dao.get(tempCourse.getId()));
        Assert.assertTrue(dao.remove(tempCourse));
    }

    @Test
    public void createTest(){
        Assert.assertTrue(dao.create(tempCourse));
        Assert.assertNotNull(dao.get(tempCourse.getName()));
        tempCourse = dao.get(tempCourse.getName());
        Assert.assertTrue(dao.remove(tempCourse));
    }

    @Test
    public void removeTest(){
        int size = dao.getAll().size();
        Assert.assertTrue(dao.create(tempCourse));
        Assert.assertTrue(dao.remove(dao.get(tempCourse.getName())));
        Assert.assertEquals(dao.getAll().size(), size);
    }

    @Test
    public void updateTest(){
        Assert.assertTrue(humanDAO.create(teacher));
        tempCourse.setTeacher(humanDAO.get(teacher.getUsername()));
        Assert.assertTrue(dao.create(tempCourse));
        Course savedCourse = dao.get(tempCourse.getName());
        savedCourse.setName("Modified course");
        Assert.assertTrue(dao.update(savedCourse));
        Course updatedCourse = dao.get(savedCourse.getName());
        Assert.assertNotSame(tempCourse, updatedCourse);
        Assert.assertEquals("Modified course", updatedCourse.getName());
        Assert.assertTrue(dao.remove(updatedCourse));
        Assert.assertTrue(humanDAO.remove(humanDAO.get(teacher.getUsername())));
    }

    @Test
    public void enrollTest(){
        Assert.assertTrue(dao.enroll(tempCourse, student));
        Assert.assertTrue(dao.unenroll(tempCourse,student));
    }
}
