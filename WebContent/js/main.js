function changeForm()
{
    document.getElementById("signin-form").className += " set-invisible";
    document.getElementById("signup-form").className = document.getElementById("signup-form").className.replace( "set-invisible", "")
}

function returnForm()
{
    document.getElementById("signup-form").className += " set-invisible";
    document.getElementById("signin-form").className = document.getElementById("signup-form").className.replace( "set-invisible", "")
}

function checkPassword(form)   
{   
    var d = document.getElementById("passwordClass");
    if(form.inputPassword.value == form.inputPassword2.value) {
      if(form.inputPassword.value.length < 6) {
        alert("Error: Password must contain at least six characters!");
        d.className += " has-error";
        return false;
      }
      if(form.inputPassword.value == form.inputUsername.value) {
        alert("Error: Password must be different from Username!");
        d.className += " has-error";
        return false;
      }
      re = /[0-9]/;
      if(!re.test(form.inputPassword.value)) {
        alert("Error: Password must contain at least one number (0-9)!");
        d.className += " has-error";
        return false;
      }
      re = /^[a-zA-Z].*/;
      if(!re.test(form.inputPassword.value)) {
        alert("Error: The first character in password must be a letter!");
        d.className += " has-error";
        return false;
      }
 
    } else {
      alert("Error: Please check that your passwords match!");
      d.className += " has-error";
      return false;
    }
    
    return true;
   
}