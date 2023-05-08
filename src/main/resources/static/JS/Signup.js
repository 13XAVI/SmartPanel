const emailInput = document.querySelector('input[type="email"]');
const passwordInput = document.querySelector('input[type="password"]');
const signUpButton = document.querySelector('#form1 button');

signUpButton.addEventListener("click", (e) => {
  e.preventDefault();
  fistForm.addEventListener("submit", (e) => e.preventDefault());
  secondForm.addEventListener("submit", (e) => e.preventDefault());

  if (emailInput.value.length > 0 && passwordInput.value.length >= 5) {
    if (isValidEmail(emailInput.value)) {
      alert("Sign up successful!");
    } else {
      alert("Please enter a valid email address.");
    }
  } else {
    alert("Please enter a valid email address and a password with at least 5 characters.");
  }
});

function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}
