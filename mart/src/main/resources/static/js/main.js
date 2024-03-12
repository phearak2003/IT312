$(document).ready(function () {
    var username = sessionStorage.getItem("username");
    var role = sessionStorage.getItem("role");

    if (username && role) {
        $.ajax({
            url: "/account/fetch-menu",
            method: "GET",
            data: { role: role },
            success: function (menuItems) {
                console.log(menuItems);
                var nav = $("nav ul.main");
                $.each(menuItems, function (index, menuItem) {
                    var li = $("<li></li>");
                    var a = $("<a></a>").attr("href", menuItem.link);
                    var icon = menuItem.icon;
                    a.append(icon)
                        .append(" ")
                        .append(document.createTextNode(menuItem.label));
                    li.append(a);
                    nav.append(li);
                });
            },
        });
    }

    if (!sessionStorage.getItem("username")) {
        window.location.href = "/account/login";
    }
    $("#greeting").text("Welcome back! " + sessionStorage.getItem("username"));

    $("#logout").on("click", function () {
        Swal.fire({
            icon: "info",
            title: "Do you really want to log out?",
            showCancelButton: true,
            confirmButtonText: "Logout",
        }).then((result) => {
            if (result.isConfirmed) {
                sessionStorage.clear();
                window.location.href = "/account/login";
            }
        });
    });
});
