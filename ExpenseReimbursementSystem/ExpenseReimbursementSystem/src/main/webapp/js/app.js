window.onload = function() {
    document.getElementById('to-login').addEventListener('click', loadLogin);
    document.getElementById('to-register').addEventListener('click', loadRegister);
    document.getElementById('to-dashboard').addEventListener('click', loadDashboard);
    document.getElementById('to-logout').addEventListener('click', logout);
}

/*
    Login component

        - loadLogin()
        - configureLogin()
        - login()
*/
async function loadLogin() {
    console.log('in loadLogin()');

    // changing the view to login
    APP_VIEW.innerHTML = await fetchView('login.view');
    DYNAMIC_CSS_LINK.href = 'css/login.css';
    configureLogin(); //calling configure
}

// here, we have event listeners to toggle an alert and invoke login() function
function configureLogin() {
    console.log('in configureLogin()');
    document.getElementById('alert-msg').hidden = true;
    document.getElementById('submit-creds').addEventListener('click', login);
}

/* 
    Login Function will:
        1. retrieve the credentials from the input fields and store them into an array 'credentials'
        2. fetches the auth servlet (login servlet) that creates a JWT/verification for the user
        3. if the response is successful, the dashboard gets loaded
*/
async function login() {
    console.log('in login()');
    let credentials = [];
    credentials.push(document.getElementById('username-cred').value);
    credentials.push(document.getElementById('password-cred').value);

    let response = await fetch('auth', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    });

    // we don't have to check the user role to change the dashboard view because we validate their id in the request view helper switch statement
    if(response.status == 200) {
        document.getElementById('alert-msg').hidden = true;
        localStorage.setItem('jwt', response.headers.get('Authorization'));
        loadDashboard();
    } else {
        document.getElementById('alert-msg').hidden = false;
    }

}

//-------------------------------------------------------------------------------------

/*
    Register component

        - loadRegister()
        - configureRegister()
        - validateUsername()
        - validatePassword()
        - register()
*/

async function loadRegister() {
    console.log('in loadRegister()');

    // change the view to register screen
    APP_VIEW.innerHTML = await fetchView('register.view');
    DYNAMIC_CSS_LINK.href = 'css/register.css';
    configureRegister(); //invoke the configureRegister()
}

// Needs work.....
function configureRegister() {
    console.log('in configureRegister()');
    document.getElementById('register-username').addEventListener('blur', validateUsername);
    document.getElementById('register-password').addEventListener('keyup', validatePassword);
    document.getElementById('register-account').addEventListener('click', register);
}

/*
    To validate,
        let's think about making a call to the database for getAll usernames, 
        make a SQL query including %LIKE% to check if there are any duplicate usernames
*/

// on blur to validate
// how is this making a call to the database
function validateUsername(event) {
    console.log('in validateUsername');
    console.log(event.target.value);

    // ajax call here to the database to check for duplicate users
}



// check username and password for length (at least more than 2 characters)

// use keyup to validate password
function validatePassword(event) {
    console.log('in validatePassword');
    console.log(event.target.value);
}


async function register() {
    console.log('in register()');


// Double check, about ers_user_id and the incrementing values
    let newUser = {
        userId: {},
        username: document.getElementById('register-username').value,
        password: document.getElementById('register-password').value,
        firstName: document.getElementById('register-fn').value,
        lastName: document.getElementById('register-ln').value,
        email: document.getElementById('register-email').value,
        roleId: 2
    };

    let response = await fetch('users', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
        
    });

    console.log(newUser);
    console.log(response);

    // here, we force the user back to the login so we only have to repeat the fetch auth process once
    if(response.status == 200) {
        loadLogin();
    } 

    let responseBody = await response.json();
    console.log(responseBody);
}

//-------------------------------------------------------------------------------------

/*
    Dashboard Employee component
        - loadDashboard()
        - configureDashboard()
        - dashboard()
 */

async function loadDashboard() {
    console.log('in loadDashboard()');
    APP_VIEW.innerHTML = await fetchView('dashboard.view'); // request view helper
    DYNAMIC_CSS_LINK.href = 'css/dashboard.css';
    configureDashboard();
}

function configureDashboard() {
    console.log('in configureDashboard()');

    // figure out which button trigures the dashboard on the html
    document.getElementById('submit-creds').addEventListener('click', login);
}

async function dashboard() {

    console.log('in dashboard');

}


//-------------------------------------------------------------------------------------

/*
    Dashboard Manager component (admin-dash)
        - loadDashboard()
        - configureDashboard()
        - dashboard()
 */

 async function loadAdminDashboard() {
    console.log('in loadAdminDashboard()');
    APP_VIEW.innerHTML = await fetchView('dashboard.view');
    DYNAMIC_CSS_LINK.href = 'css/admin-dash.css';
    configureAdminDashboard();
 }

 //figure out which button in the html, triggers the dashboard
 function configureAdminDashboard() {
     console.log('in configureAdminDashboard()');
     document.getElementById('submit-creds').addEventListener('click', login);

 }

 async function adminDashboard() {
    console.log('in dashboard');
 }

//-------------------------------------------------------------------------------------
async function fetchView(uri) {
    let response = await fetch(uri, {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')
        }
    });

    if(response.status == 401) loadLogin();
    return await response.text();
}
//---------------------------------------------------------------------


const APP_VIEW = document.getElementById('app-view');
const DYNAMIC_CSS_LINK = document.getElementById('dynamic-css');





//========================================================================================================================================



