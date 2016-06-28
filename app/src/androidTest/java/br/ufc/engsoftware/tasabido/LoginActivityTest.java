package br.ufc.engsoftware.tasabido;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufc.engsoftware.models.Perfil;

import static org.junit.Assert.*;

/**
 * Created by RH on 27/06/2016.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{

    private static final String userName = "usertest";
    private static final String password = "usertest";
    private static final String userName1 = "User123";
    private static final String password1 = "user123";

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

    }

    @Test
    public void testAlreadyLoged() throws Exception {

    }

    @Test
    public void testLogin() throws Exception {

    }

    @Test
    public void testOnActivityResult() throws Exception {

    }

    @Test
    public void testOnBackPressed() throws Exception {

    }

    @Test
    public void testOnLoginSuccess() throws Exception {
        solo.unlockScreen();
        solo.enterText(0, userName1);
        solo.typeText(1, password1);
        solo.clickOnView(solo.getView(br.ufc.engsoftware.tasabido.R.id.btn_login));
        solo.searchText(userName1);
        solo.searchText(password1);
        solo.assertCurrentActivity("Expected PaginaPrincipalActivity", PaginaPrincipalActivity.class);
    }

    @Test
    public void testOnLoginFailed(String userName, String password) throws Exception {
        solo.unlockScreen();
        solo.enterText(0, userName);
        solo.typeText(1, password);
        solo.clickOnView(solo.getView(br.ufc.engsoftware.tasabido.R.id.btn_login));
        boolean userNotFound = solo.searchText(userName) && solo.searchText(password);
        assertFalse("User not founded", userNotFound);
    }

    @Test
    public void testValidate() throws Exception {
        final String userName = "";
        final String password = "";
        if(userName.isEmpty() && password.isEmpty()){
            testOnLoginFailed(userName, password);
        }
    }

    @Test
    public void testConcatenateParam() throws Exception {
        Perfil perfil = new Perfil(userName1, password1);
        String param = "username=";
        param += perfil.getUsuario();
        param += "&";
        param += "password=";
        param += perfil.getSenha();
        assertEquals("username = " + userName1 + " & password = " + password1, param);
    }

    @Test
    public void testLembrarLoginSenha() throws Exception {

    }

    @Test
    public void testSalvarLoginSenha() throws Exception {

    }
}