package br.ufc.engsoftware.tasabido;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Perfil;

import static org.junit.Assert.*;

/**
 * Created by RH on 27/06/2016.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{

    private static final String userName = "User123";
    private static final String password = "user123";

    private Solo solo;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testOnCreate() throws Exception {
        solo.assertCurrentActivity("Expected LoginActivity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.link_signup));
        solo.assertCurrentActivity("Expected CadastroActivity", CadastroActivity.class);
    }

    @Test
    public void testAlreadyLoged() throws Exception {
        solo.assertCurrentActivity("Expected LoginActivity", LoginActivity.class);
    }

    @Test
    public boolean testLogin(String userName, String password) throws Exception {
        solo.unlockScreen();
        solo.enterText(0, userName);
        solo.typeText(1, password);
        solo.clickOnView(solo.getView(br.ufc.engsoftware.tasabido.R.id.btn_login));
        return solo.searchText(userName) && solo.searchText(password);
    }

    @Test
    public void testOnActivityResult() throws Exception {

    }

    @Test
    public void testOnBackPressed() throws Exception {

    }

    @Test
    public void testOnLoginSuccess() throws Exception {
        testLogin(userName, password);
        solo.assertCurrentActivity("Expected PaginaPrincipalActivity", PaginaPrincipalActivity.class);
    }

    @Test
    public void testOnLoginFailed() throws Exception {
        final String userName = "usertest";
        final String password = "usertest";
        boolean userNotFound = testLogin(userName, password);
        assertFalse("User not founded", userNotFound);
    }

    @Test
    public void testValidate() throws Exception {
        /*final String userName = "";
        final String password = "";
        if (userName.equals("") && password.equals("")) {
            testOnLoginFailed();
        }*/
    }

    @Test
    public void testConcatenateParam() throws Exception {
        Perfil perfil = new Perfil(userName, password);
        String param = "username = ";
        param += perfil.getUsuario();
        param += " & ";
        param += "password = ";
        param += perfil.getSenha();
        assertEquals("username = " + userName + " & password = " + password, param);
    }

    @Test
    public void testLembrarLoginSenha() throws Exception {

    }

    @Test
    public void testSalvarLoginSenha() throws Exception {

    }
}