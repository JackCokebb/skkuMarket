const checkLogin = () => {
  const user_id = sessionStorage.getItem("user_id");
  if (user_id) {
    $("#headerBtn-login").css("display", "none");
    $("#headerBtn-logout").css("display", "flex");
    $("#userInfoText").html(sessionStorage.getItem("nickname"));
  } else {
    $("#headerBtn-login").css("display", "flex");
    $("#headerBtn-logout").css("display", "none");
  }
};

const logout = () => {
  sessionStorage.removeItem("user_id");
  sessionStorage.removeItem("nickname");
  location.href = "../../index.html";
};


// load
$(function () {
  checkLogin();
  const urlStr = window.location.href;
  const url = new URL(urlStr);
  const urlParams = url.searchParams;

  const qsPostId = urlParams.get("post_id");

  // get user current nickname
  let userNickname = sessionStorage.getItem("nickname");
  if (userNickname) {
    $(".com_userInfo")
      .append(`<i class="bi bi-person-circle" id="user_profile"></i>
        ${userNickname}`);
  } else {
    $(".com_userInfo").append(`You can write a comment after logging in.
`);
  }

  // get user_id from post_id
  $.ajax({
    type: "GET",
    url: `http://localhost:3000/userposts/post/${qsPostId}`,
    data: "",
    dataType: "json",
    success: (response) => {
      let getUserId = response.user_id;

      $.ajax({
        type: "GET",
        url: `http://localhost:3000/users/${getUserId}`,
        data: "data",
        dataType: "json",
        success: (response) => {
          if (response.user_nickname != null) {
            $(".author").append(
              `<i class="bi bi-person-circle" id="post_userInfo"></i>&nbsp${response.user_nickname}`
            );
          }
        },
        error: (err) => {
          console.log(err);
        },
      });
    },
    error: (err) => {
      console.log(err);
    },
  });

  /**
   * comment feature
   * */

  $.ajax({
    type: "GET",
    url: `http://localhost:3000/comments?${qsPostId}`,
    data: "",
    dataType: "json",
    success: (response) => {
      let commentArr = response.result;
      let newArr = commentArr.filter((el) => el.post_id == qsPostId);
      console.log(newArr);

      newArr.forEach((el) => {
        console.log("el.user_id_from", el.user_id_from);

        $.ajax({
          type: "GET",
          url: `http://localhost:3000/users/${el.user_id_from}}`,
          data: "",
          dataType: "json",
          success: (response) => {
            console.log("res", response);
            
            $(".comment_list").append(
              `<li>
                    <div class='card my-4'>
                        <h5 class='card-header'>
                            <div class='com_userInfo'><i class='bi bi-person-circle' id='user_profile'></i>
                                ${response.user_nickname}</div>
                        </h5>
                        <div class='card-body'>
                            <form name='comment-form' autocomplete='off'>
                                <div class='form-group'>
                                    <div class='commentList_content'>${el.comment}</div>
                                </div>
                            </form>
                        </div>
                    </div>
                </li>`
            );
          },
          error: (err) => {
            console.log(err);
          },
        });
      });
    },
    error: (err) => {
      console.log(err);
    },
  });

  // get post information from post_id
  $.ajax({
    type: "GET",
    url: `http://localhost:3000/posts/${qsPostId}`,
    data: "",
    dataType: "json",
    success: (response) => {
      $("#image").attr("src", response.image);
      $(".title").append(response.title);
      $(".price").append(response.price + "원");
      $(".content").append(response.description);
    },
    error: (err) => {
      console.log(err);
    },
  });

  // reply button event
  $("#reply_btn").click((e) => {

    if (!userNickname) {
      alert("You can write a comment after logging in.");
      location.href = "./login.html";
    } else {
      let commentValue = $(".form-control").val();

      $(".form-control").val("");

      let sessUserId = sessionStorage.getItem("user_id");

      let commentData = {
        post_id: qsPostId,
        user_id_from: sessUserId,
        comment: commentValue,
      };

      $.ajax({
        type: "POST",
        url: "http://localhost:3000/comments/",
        data: commentData,
        dataType: "json",
        success: (response) => {
          console.log(response);
          location.reload();
        },
        error: (err) => {
          console.log(err);
        },
      });
    }
  });
});
