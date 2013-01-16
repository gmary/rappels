'use strict';

/* Services */
var rappelsService = angular.module('rappels.services', ['ngResource']);

rappelsService.factory('Rappel', function($resource){
  return $resource('/rappels/:rappelId', {}, {
    active: {method:'GET', params:{rappelId:'active'}, isArray:true}
  });
});

rappelsService.factory('User', function($resource){
  return $resource('/user/:userId', {}, {
    authenticate: {method:'POST', params:{userId:'authenticate'}}
  });
});