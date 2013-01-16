'use strict';


// Declare app level module which depends on services
angular.module('rappels', ['rappels.directives','rappels.services']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: HomeCtrl});
    $routeProvider.when('/rappels/active', {templateUrl: 'partials/rappels-active.html', controller: RappelsActiveCtrl});
    $routeProvider.otherwise({redirectTo: '/home'});
  }]);
