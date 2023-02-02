$("#login_button").click(() => {
  let email = $("#floatingInput").val();
  let password = $("#floatingPassword").val();

  let data = {
    user_email: email,
    user_password: password,
  };

  if (email == "") {
    alert("Please input email!");
  } else if (password == "") {
    alert("Please input password");
  } else {
    $.ajax({
      type: "POST",
      url: `http://localhost:3000/users/email`,
      data,
      dataType: "json",
      success: (response) => {
        if (!response.length) {
          alert("Successful login!");
          console.log(response.user_id);
          console.log(response.user_nickname);
          sessionStorage.setItem("user_id", response.user_id);
          sessionStorage.setItem("nickname", response.user_nickname);
          console.log(history.go(-1));
        }
      },
      error: (err) => {
        console.log(err);
        alert("no user info!");
      },
    });
  }
});

$("#signup_btn").click(() => {
  location.href = "signup.html";
});
