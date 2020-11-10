
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
    public void before() {
    }

    @Test
    public void after() {
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void add48Hours() {
    }
}
