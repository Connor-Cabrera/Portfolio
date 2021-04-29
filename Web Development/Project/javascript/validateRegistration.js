window.addEventListener("DOMContentLoaded", domLoaded);

function validate(event){

	let form = document.querySelector("#registrationForm");
	let firstName = form.firstName;
	let lastName = form.lastName;
	let streetAddress = form.streetAddress;
	let city = form.city;
	let states = form.states;
	let zipCode = form.zipCode;
	let phoneNumber = form.phoneNumber;
	let phoneType = form.phoneType;
	let emailAddress = form.emailAddress;
	
	//Data entered/selected and longer than 1 char
	if(firstName.value == "" || firstName.value.length < 2){
		
		alert("Please provide your first name!");
		firstName.focus();
		event.preventDefault();
		return;
	}
	//Data entered/selected and longer than 1 char
	if(lastName.value == "" || lastName.value.length < 2){
		
		alert("Please provide your last name!");
		lastName.focus();
		event.preventDefault();
		return;
	}
	//Data entered/selected and longer than 1 char
	if(streetAddress.value == "" || streetAddress.value.length < 2){
		
		alert("Please provide your street address!");
		streetAddress.focus();
		event.preventDefault();
		return;
	}
	//Data entered/selected and longer than 1 char
	if(city.value == "" || city.value.length < 2){
		
		alert("Please provide your city!");
		city.focus();
		event.preventDefault();
		return;
	}
	//Data entered/selected and longer than 1 char
	if(states.value == "-1"){
		
		alert("Please provide your state!");
		states.focus();
		event.preventDefault();
		return;
	}
	//Data entered/selected
	if(zipCode.value == ""){
		
		alert("Please provide your zip code!");
		zipCode.focus();
		event.preventDefault();
		return;
	}
	//Data is numeric and 5 digits long
	if(zipCode.value.length != 5 || isNaN(zipCode.value)){
		alert("Please provide your zip code in the following format: 5 Digits");
		zipCode.focus();
		event.preventDefault();
		return;
	}
	//Data entered/selected
	if(phoneNumber.value == ""){
		
		alert("Please provide your phone number!");
		phoneNumber.focus();
		event.preventDefault();
		return;
	}
	//Data is numeric and 10 digits long
	if(phoneNumber.value.length != 10 || isNaN(phoneNumber.value)){
		
		alert("Please provide your phone numberin the following format: 10 Digits");
		phoneNumber.focus();
		event.preventDefault();
		return;
	}
	//Data entered/selected
	if(phoneType.value == ""){
		
		alert("Please provide your phone type!");
		event.preventDefault();
		return;
	}
	//Data entered/selected
	if(emailAddress.value == ""){
		
		alert("Please provide your email address!");
		emailAddress.focus();
		event.preventDefault();
		return;
	}
	//Contains an '@' followed by at least one character
	//Contains an '.' followed by at least one character
	let atpos = emailAddress.value.indexOf("@");
	let dotpos = emailAddress.value.lastIndexOf(".");
	console.log(atpos);
	console.log(dotpos);
	if(atpos == -1 || dotpos == -1){
		alert("Please enter correct email ID: name@domain.com");
		emailAddress.focus();
		event.preventDefault();
		return;
	}
	if((dotpos - atpos < 2) || ((dotpos + 1) == emailAddress.value.length)){
		alert("Please enter correct email ID: name@domain.com");
		emailAddress.focus();
		event.preventDefault();
		return;
	}
	
	return true;
	
}
function domLoaded(){
	//Event Listener for form submission
	let formWidget = document.getElementById("registrationForm");
	formWidget.addEventListener("submit", validate);
}