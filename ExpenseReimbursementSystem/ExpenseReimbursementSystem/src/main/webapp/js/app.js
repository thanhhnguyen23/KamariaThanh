window.onload = function() {
    document.getElementById('to-login').addEventListener('click', loadLogin);
    document.getElementById('to-register').addEventListener('click', loadRegister);
    document.getElementById('to-dashboard').addEventListener('click', loadDashboard);
    document.getElementById('to-logout').addEventListener('click', logout);
}

// /*
//     Login component

//         - loadLogin()
//         - configureLogin()
//         - login()
// */
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

    if(response.status == 200) {
        document.getElementById('alert-msg').hidden = true;
        localStorage.setItem('jwt', response.headers.get('Authorization'));
        loadDashboard();
    } else {
        document.getElementById('alert-msg').hidden = false;
    }

}

// //-------------------------------------------------------------------------------------

// /*
//     Register component

//         - loadRegister()
//         - configureRegister()
//         - validateUsername()
//         - validatePassword()
//         - register()
// */

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
        ers_user_id: {},
        ers_username: document.getElementById('register-username').value,
        ers_password: document.getElementById('register-password').value,
        user_first_name: document.getElementById('register-fn').value,
        user_last_name: document.getElementById('register-ln').value,
        user_role_id: 2
    };

    let response = await fetch('users', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    });

    let responseBody = await response.json();
    console.log(responseBody);
}

//-------------------------------------------------------------------------------------

// /*
//     Dashboard component
//         - loadDashboard()
//  */

// async function loadDashboard() {
//     console.log('in loadDashboard()');
//     APP_VIEW.innerHTML = await fetchView('dashboard.view');
//     DYNAMIC_CSS_LINK.href = 'css/dashboard.css';
//     configureDashboard();
// }

// function configureDashboard() {
//     console.log('in configureDashboard()');
// }

// //-------------------------------------------------------------------------------------
// async function fetchView(uri) {
//     let response = await fetch(uri, {
//         method: 'GET',
//         mode: 'cors',
//         headers: {
//             'Authorization': localStorage.getItem('jwt')
//         }
//     });

//     if(response.status == 401) loadLogin();
//     return await response.text();
// }

// //-------------------------------------------------------------------------------------

// const APP_VIEW = document.getElementById('app-view');
// const DYNAMIC_CSS_LINK = document.getElementById('dynamic-css');