$("#signup_btn").click(() => {
  let email = $("#floatingInput").val();
  let name = $("#nameInput").val();
  let nickName = $("#NicknameInput").val();
  let password = $("#floatingPassword").val();
  let confirmPassword = $("#confirmPassword").val();

  // Set validation for a sign up
  if (password != confirmPassword) {
    alert("Password is not matching");
  } else if (email === "") {
    alert("Please input email!");
  } else if (name === "") {
    alert("Please input name!");
  } else if (nickName === "") {
    alert("Please input nickname!");
  } else if (password == "") {
    alert("Please input password!");
  } else {
    let data = {
      user_name: name,
      user_nickname: nickName,
      user_email: email,
      user_password: password,
    };

    $.ajax({
      type: "POST",
      url: "http://localhost:3000/users/",
      data,
      dataType: "json",
      success: (response) => {
        console.log("success!");
      },
      error: (err) => {
        console.log("error");
      },
    });

    alert("You have successfully registered as a member!");
    location.href = "../../index.html";
  }
});
