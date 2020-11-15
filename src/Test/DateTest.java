
import org.junit.*;
import TraceIt.Date;
public class DateTest {

    @Test
    public void test001_compareTo() {
        Date fechaAComparar= new Date("22/07/2020");
        Date fechaDespues= new Date("23/07/2020");
        Date fechaAntes= new Date("21/07/2020");

        Assert.assertEquals( 1,fechaAComparar.compareTo(fechaAntes));
        Assert.assertEquals( -1,fechaAComparar.compareTo(fechaDespues));
        Assert.assertEquals( 0,fechaAComparar.compareTo(fechaAComparar));

    }

    @Test
    public void test002_before() {
        Date fechaAntes= new Date("02/07/2020");
        Date fecha= new Date("21/07/2020");

        Assert.assertTrue(fechaAntes.before(fecha));
    }

    @Test
    public void test003_after() {
        Date fechaDespues= new Date("21/07/2020");
        Date fecha= new Date("02/07/2020");

        Assert.assertTrue(fecha.after(fechaDespues));
    }

    @Test
    public void test003_Equals() {
        Date fecha2= new Date("02/07/2020");
        Date fecha= new Date("02/07/2020");
        Date fechaDif= new Date("21/07/2020");

        Assert.assertTrue(fecha.equals(fecha2));
        Assert.assertFalse(fecha.equals(fechaDif));
    }

    @Test
    public void testToString() {
    }

    @Test
    public void add48Hours() {
    }
}
