window.onload = function() {
    document.getElementById('to-login').addEventListener('click', loadLogin);
    document.getElementById('to-register').addEventListener('click', loadRegister);
    document.getElementById('to-home').addEventListener('click', loadLogin);
    // document.getElementById('to-profile').addEvent
    // document.getElementById('to-logout').addEventListener('click', logout);
}




// document.getElementById('create-reimb').addEventListener('click', loadReimbursement);


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
var credentials = [];
async function login() {
    console.log('in login()');
    // var credentials = [];
    credentials.push(document.getElementById('username-cred').value);
    credentials.push(document.getElementById('password-cred').value);

    
    console.log(credentials);

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
        if (credentials[0] == 'admin_kd' || credentials[0] == 'admin_tn') {
            loadAdminDashboard();
        }

        else {
            loadDashboard();
        } 

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

    localStorage.setItem('username', document.getElementById('register-username').value);
    localStorage.setItem('firstName', document.getElementById('register-fn').value);
    localStorage.setItem('lastName', document.getElementById('register-ln').value);
    localStorage.setItem('email', document.getElementById('register-email').value);

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
    dashboard();
    
}

// function configureDashboard() {
//     console.log('in configureDashboard()');

//     // figure out which button trigures the dashboard on the html
//     document.getElementById('submit-creds').addEventListener('click', login);
//     dashboard();
// }

async function dashboard() {

    console.log('in dashboard');
    console.log(credentials[0]); //this should log the username
    document.getElementById('create-reimb').addEventListener('click', loadReimbursement);

    //have a function that loads the view-reimb.html
    document.getElementById('view-reimb').addEventListener('click', loadViewReimbursements);
    // document.getElementsByClassName('btn btn-secondary').addEventListener('click', showReimbursement);
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
    APP_VIEW.innerHTML = await fetchView('admin-dashboard.view');
    DYNAMIC_CSS_LINK.href = 'css/admin-dash.css';
    adminDashboard();
 }

 //figure out which button in the html, triggers the dashboard
//  function configureAdminDashboard() {
//      console.log('in configureAdminDashboard()');
//      document.getElementById('submit-creds').addEventListener('click', login);

//  }

 async function adminDashboard() {
    console.log('in adminDashboard');
    console.log(credentials[0]); //this should log the username
    document.getElementById('create-reimb-admin').addEventListener('click', loadReimbursement);

    //have a function that loads the view-reimb.html
    document.getElementById('view-reimb-admin').addEventListener('click', loadAdminViewReimbursements);
    // document.getElementsByClassName('btn btn-secondary').addEventListener('click', showReimbursement);

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


/*
    Reimbursemnt Component

        -loadReimbursement()
        -configureReimbursement()
        -reimbursement()
*/


//GO BACK AND CHANGE REIMBURSEMENT
async function loadReimbursement() {
    console.log('in loadReimbursement()');

    APP_VIEW.innerHTML = await fetchView('new-reimb.view');
    document.getElementById('alert-empty').hidden = true;
    document.getElementById('alert-nan').hidden = true;
    document.getElementById('alert-description').hidden = true;
    configureReimbursement();
}

function configureReimbursement() {
    console.log('in configureReimb()');
    document.getElementById('new-reimbursement').addEventListener('click', createReimbursement);

}


// add validation for fields not being empty
// function configureReimbursement() {
//     console.log.log('in configureReimbursement');
//     let reimb_user = document.getElementById('reimbursement-username').value;
//     let reimb_type = document.getElementById('reimbursement-type').value;
//     let reimb_amount = document.getElementById('reimbursement-amount').value;
//     let reimb_description = document.getElementById('reimbursement-description').value;

//     if ((reimb_user == '') || (reimb_type == '') || (reimb_amount == '') || (remb_description == '')) {
//         document.getElementById('alert-empty').hidden = false;
//     } else document.getElementById('alert-empty').hidden = true;

//     if (isNaN(reimb_amount)) {
//         document.getElementById('alert-nan').hidden = false;
//     } else document.getElementById('alert-nan').hidden = true;

//     if (reimb_description > 250 ) document.getElementById('alert-description').hidden = false;
//     else document.getElementById('alert-description').hidden = true;
// }


var authorHolder = document.getElementById('authorHolder');
var amountHolder = document.getElementById('amountHolder');
var descriptionHolder = document.getElementById('descriptionHolder');
var typeHolder = document.getElementById('typeHolder')


async function createReimbursement() {
    console.log('in createReimbursement()');

    let newReimb = {
        reimbId: {},
        amount: document.getElementById('reimbursement-amount').value,
        resolverId: 1,
        statusId: 1,
        reimbDescription: document.getElementById('reimbursement-description').value,
        authorId: document.getElementById('reimbursement-username').value,
        typeId: document.getElementById('new-reimb-type').value
    };

    // here, we are fetching a REIMBURSEMENTS servlet that technically does not exist yet but will revisit later
    let response = await fetch('reimbursements', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newReimb)
    });

    console.log(newReimb.reimbId);
    console.log(response);
    // console.log(responseBody);

    if (response.status == 200) {
        console.log('the status is 200...');
        if (credentials[0] == 'admin_kd' || credentials[0] == 'admin_tn') {
            loadAdminViewReimbursements();
        }

        else {
            loadViewReimbursements();
        }
    }

    let responseBody = await response.json(); // username field must be given userid
    console.log(responseBody);

    // document.getElementById('new-reimbursement').addEventListener('click', alert('Your reimbursement has been submitted!'));
    // alert('Your reimbursement has been submitted!');
}


//=======================================================
// ADMIN Reimbursement View
async function loadAdminViewReimbursements() {
    console.log('in loadAdminViewReimbursements()');

    APP_VIEW.innerHTML = await fetchView('view-reimb-admin.view');
    DYNAMIC_CSS_LINK.href = 'css/app.css';
    // document.getElementById('view-admin-reimb').addEventListener('click', showReimbursement);
   
    // gonna give us a APP_VIEW error 
    document.getElementById('approve').addEventListener('click',  approveReimb);

    getAllReimbs();
    
}





//=======================================



// EMPLOYEE Reimbursement View
async function loadViewReimbursements() {
    console.log('in loadViewReimbursements()');

    APP_VIEW.innerHTML = await fetchView('view-reimb.view');
    DYNAMIC_CSS_LINK.href = 'css/app.css';
    // document.getElementById('view-reimb').addEventListener('click', showReimbursement);
    getReimbById();

}


//////////////////////////////////////////////////////////////////////////////////////////
// get reimbursement 
//////////////////////////////////////////////////////////////////////////////////////////

// get all reimbursements for manager 
async function getAllReimbs() {

    console.log('in getAllReimbs()');

    let response = await fetch('reimbursements');
    console.log(response);

    let responseBody = await response.json();
    console.log(responseBody);

    if (response.status == 200) {
        console.log(response.status);

        //looping through the responseBody and getting the values for each object
        for (let i =0; i < responseBody.length; i++) {
            var reimbId = responseBody[i]['reimbId'];
            // console.log(reimbId);
            var amount = responseBody[i]['amount'];
            var reimbDescription = responseBody[i]['reimbDescription'];
            var authorId = responseBody[i]['authorId'];
            var statusId = responseBody[i]['statusId'];
            var typeId = responseBody[i]['typeId'];
            
            // if statements to display the type of reimbursement
            if (typeId == 1) {
                var typeId2 = 'LODGING';
            }

            if (typeId == 2) {
                typeId2 = 'TRAVEL';
            }

            if (typeId == 3) {
                typeId2 = 'FOOD';
            }

            if (typeId == 4) {
                typeId2 = 'OTHER';
            }

            // if statements to display the status
            if (statusId == 1) {
                var statusId2 = 'PENDING';
            }

            if (statusId == 2) {
                statusId2 = 'APPROVE';
            }

            if (statusId == 3) {
                var statusId2 = 'DONE';
            }

            if (statusId == 4) {
                var statusId2 = 'DENIED';
            }

            //create button to encapsulate the entire table row
            // when you click
            let approveButton= document.createElement('button');
            approveButton.setAttribute('type', "button");
            approveButton.setAttribute('class', "btn btn-primary");
            // button.setAttribute('data-toggle', "modal");
            // button.setAttribute('data-target', "#exampleModal");
            approveButton.setAttribute('id', "approve" + i);

            let denyButton= document.createElement('button');
            denyButton.setAttribute('type', "button");
            denyButton.setAttribute('class', "btn btn-primary");
            denyButton.setAttribute('id', "deny" + i);
            
            
            //for each object, create a new table row
            let tableRow = document.createElement('tr');
            tableRow.setAttribute('id', "tableRow" + i); //increment the id

            //for each row, create the table data elements
            let tableData = document.createElement('td');
            let tableData1 = document.createElement('td');
            let tableData2 = document.createElement('td');
            let tableData3 = document.createElement('td');
            let tableData4 = document.createElement('td');
            let tableData5 = document.createElement('td');
            let tableButton1 = document.createElement('td'); //approve
            let tableButton2 = document.createElement('td'); //deny

            //set attributes for the table datas
            // tableData.setAttribute
           
            //add the values to the table data
            tableData.append(reimbId);
            tableData3.append(authorId);
            tableData1.append(amount);
            tableData2.append(reimbDescription);
            tableData4.append(statusId2);
            tableData5.append(typeId2);
            tableButton1.append(approveButton);
            tableButton2.append(denyButton);


            //append each element, to each row
            tableRow.append(tableData); // blank, later we add reimbId
            tableRow.append(tableData3);
            tableRow.append(tableData1);
            tableRow.append(tableData2);
            tableRow.append(tableData4);
            tableRow.append(tableData5);
            tableRow.append(tableButton1);
            tableRow.append(tableButton2);

            //the table already exists so just append the table rows to the table
            let table = document.getElementById('empReimbTable');
            table.append(tableRow);

            

            document.getElementById('approve' + i).addEventListener('click', approveReimb);
        }// end of for loop

        
    

}// end of for loop
//////////////////////////////////////////////////////////////////////////////////////////


    
   
}



// get reimbursements by id to show on the users dashboard
// EMPLOYEE REIMBURSEMNET VIEW -- TN
//----------------------------------
async function getReimbById() {
    console.log('in getReimbById()');

    let authorId = {
        authorId: document.getElementById('reimbursement-username')
    }

    console.log(authorId);

    let response = await fetch('reimbById', {
        method: 'GET', 
        mode: 'cors',
        headers: {
            // 'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(authorId)
    });

    if (response.status == 200) {
        // populate the user's table on the employee dashboard
        console.log(response.status);
    }

    let responseBody = await response.json();
    console.log("response: " + responseBody);

    console.log(response);

    for ( let i = 0; i < responseBody.length; i++){
        var reimbId = responseBody[i]['reimbid'];
        console.log('reimbId: ' + reimbId);

        // populating information needed for tables
        var amount = responseBody[i]['amount'];


        // reimbursement type switches

        if (typeId == 1) {
            var typeId2 = 'LODGING';
        }

        if (typeId == 2) {
            typeId2 = 'TRAVEL';
        }

        if (typeId == 3) {
            typeId2 = 'FOOD';
        }

        if (typeId == 4) {
            typeId2 = 'OTHER';
        }

        // if statements to display the status
        if (statusId == 1) {
            var statusId2 = 'PENDING';
        }

        if (statusId == 2) {
            statusId2 = 'APPROVE';
        }

        if (statusId == 3) {
            var statusId2 = 'DONE';
        }

        if (statusId == 4) {
            var statusId2 = 'DENIED';
        }

        // creating elements for information to be populated
        let clickable = document.createElement('a');
        clickable.setAttribute('href', 'exampleModal');

        let container = document.getElementById('container');

        // for each object, create a new table row
        let tableRow = document.createElement('tr');

        clickable.append(container);


        // for each row, create the table data elements
        let tableData = document.createElement('td');
        let tableData1 = document.createElement('td');
        let tableData2 = document.createElement('td');
        let tableData3 = document.createElement('td');
        let tableData4 = document.createElement('td');
        let tableData5 = document.createElement('td');
        let tableButton1 = document.createElement('td');
        let tableButton2 = document.createElement('td');

        // add the values to the tables
        tableData.append(reimbId);
        tableData3.append(authorId);
        tableData1.append(amount);
        tableData2.append(reimbDescription);
        tableData4.append(statusId2);
        tableData5.append(typeId2);
        tableButton1.append(approveButton);
        tableButton2.append(denyButton);


        // append each element, to each row
        tableRow.append(tableData);
        // look at line around 510

    }
}


<<<<<<< HEAD
=======




// on approve button click, do this function
// document.getElementById('approve').addEventListener('click', approveReimb);
// async function approveReimb() {
//     console.log('in approveReimb()');
//     let response = await fetch ('updateReimbStatus', {
//         method: 'POST',
//         mode: 'cors',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body:JSON.stringify()
//     });
// }
>>>>>>> 2901f87e896b947176ce0b517e3cf94e5dcd2ea4
async function approveReimb() {
    console.log('in approveReimb()');

    let target = event.target.id + '';
    let target2 = event.target.id + '';
    target2.slice(target2.length-1);

    target = target.slice(target.length-1);
    let tableInfo = document.getElementById('tableRow' + target);

    let newType = tableInfo.childNodes[5].innerText;
    console.log(newType);

    switch(newType) {

        case("LODGING"):
            newType = 1;
            break;
        case("TRAVEL"):
            newType = 2;
            break;
        case("FOOD"):
            newType = 3;
            break;
        case("OTHER"):
            newType = 4;
            break;
    }

//     reimb id"50"
// authorId: "9"
// amount "75000"
// description "mexico"
// resolverId: 1
// statusId: 2
// typeId: ""

    let updatedReimb = {
        reimbId: tableInfo.childNodes[0].innerText,
        authorId: tableInfo.childNodes[1].innerText,
        amount: tableInfo.childNodes[2].innerText,
        reimbDescription: tableInfo.childNodes[3].innerText,
        statusId: 2,
        typeId: newType
    };

    console.log(updatedReimb);


    let response = await fetch ('updateReimbStatus', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body:JSON.stringify(updatedReimb)
    });

    console.log(response);
    let responseBody = await response.json();
    // console.log(responseBody);
    return responseBody;


    
}



//-------------------------------------------------------------

const APP_VIEW = document.getElementById('app-view');
const DYNAMIC_CSS_LINK = document.getElementById('dynamic-css');

//========================================================================================================================================
