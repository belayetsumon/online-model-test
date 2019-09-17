/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



// J query Form Validator

// file validation for pic
$(document).ready(function () {
    $.validate({
        modules: 'file'
    });
});



//template sidebar menu

 $(document).ready(function () {
            $('#sidebarCollapse').on('click', function () {
                $('#sidebar').toggleClass('active');
            });
        });