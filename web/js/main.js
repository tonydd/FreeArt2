/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

$(document).ready(function () 
{
    /*
    $("img.display").mouseenter(function ()
    {
        $(this).next().fadeIn(250);
    });
    
    $("img.display").mouseout(function ()
    {
       $(this).next().fadeOut(250); 
    });
    */
    
    /* --- Chargement des catégories --- */
    $.get(
        'MainController',
        {
            data : "categories"
        },
        function(data)
        {
            alert(data);
            $("ul#categories").html(data);
        }
    );
   
   $("img#login").click( function()
    {
        /*
       $.blockUI(
            {
                message : document.getElementById('div_login').innerHTML,
                css : {padding : "50px", cursor: "default"}
            }
        );
        */
       
       
    });
    
     $( "#dialog-form" ).dialog({
            autoOpen: false,
            height: 300,
            width: 350,
            modal: true,
            buttons: {
                "Create an account": function() {
                    $( this ).dialog( "close" );
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                $( this ).dialog( "close" );
            }
        });
});

function unblockUI()
{
    $.unblockUI();
}

function logUser()
{
    var login = $("#txt_login").val();
    var passwd = $("#txt_password").val();
    $.post(
        "MainController",
        {
            action : "login",
            txt_login: login,
            txt_password: passwd
        },
        function(data)
        {
            if (data === "LOGGED")
            {
                $.unblockUI();
                document.getElementById('login').setAttributeNS(null, src, "img/ok.png");
            }
            else
            {
                $.unblockUI();
                alert("LOGIN FAILED");
            }
        }
    );
}


