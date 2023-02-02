

const checkLogin = () => {
    const user_id = sessionStorage.getItem("user_id");
    if (!user_id) {
        alert("Login First!!!")
        location.href = "./login.html"
    }
}

const logout = () => {
    sessionStorage.removeItem("user_id")
    sessionStorage.removeItem("nickname")
    location.href = "../../index.html"
}

const loadUserInfo = async () => {
    const user_id = sessionStorage.getItem("user_id");
    $.ajax({
        type: "GET",
        url: `http://localhost:3000/users/${user_id}`,
        dataType: "json",
        success: (response) => {
            console.log("user : " + JSON.stringify(response) );
            $("#userName").text(response.user_name);
            $("#userNickname").text(response.user_nickname);
            $("#userEmail").text(response.user_email);
            $("#userInfoText").text(response.user_nickname);

        },
        error: () => {
            console.log("Get userpost list failed!");
            alert("Get userpost list failed!");
        },
    });
}

const loadComments = async () => {
    const user_id = sessionStorage.getItem("user_id");
    $.ajax({
        type: "GET",
        url: `http://localhost:3000/userposts/user/${user_id}`,
        //data: "hellow",
        dataType: "json",
        success: (response) => {
            if (response.length > 0) {
                const productList_div = document.querySelector("#userPostList");
                productList_div.innerHTML = "";
            }

            response.forEach((userpost) => {
                
                $.ajax({
                    type: "GET",
                    url: `http://localhost:3000/posts/${userpost.post_id}`,
                    dataType: "json",
                    success: (response) => {
                        renderCommentList(response);
                    },
                    error: (err) => {
                        console.log("Get post list failed! " + err);
                        alert("Get post list failed!");
                    },
                });
            });
        },
        error: () => {
            console.log("Get userpost list failed!");
            alert("Get userpost list failed!");
        },
    });
};


const renderCommentList = (post) => {
    const productList_div = document.querySelector("#userPostList");
    productList_div.innerHTML += `
    <a href="./detail.html?post_id=${post.post_id}" class="list-group-item list-group-item-action">
        ${post.title}
    </a>
    `;

    return;
};

$(document).ready(function () {
    checkLogin();
    loadComments();
    loadUserInfo();
});
