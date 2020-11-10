import TraceIt.Usuario;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsuarioTest {
    @Test
    public void test001_VerSiElUsuarioConoceSuNombre(){
        Usuario usuario= new Usuario("43627559","1122694072","Nordelta", "Timoteo",0);

        assertEquals("43627559", usuario.cuil);
    }
}