'use strict';

/* Controllers */

/* Home */
function HomeCtrl($scope) {

}



function LoginController($scope, User, authService) {
    $scope.submit = function() {
      User.authenticate({},{email: $scope.email, password: $scope.password},function() {
        authService.loginConfirmed();
      })
    }
}


function RappelsActiveCtrl($scope, Rappel) {
    $scope.rappels = Rappel.active();
    $scope.orderProp = 'name';
}



