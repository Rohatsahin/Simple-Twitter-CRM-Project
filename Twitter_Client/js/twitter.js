var App = angular.module('nameApp',[]);

App.controller('nameCtrl', ['$scope','$http','$filter','$window',function($scope,$http,$filter,$window){


$scope.url='http://localhost:8080/rest/twit/list';
$http.get($scope.url).success(function(data)
{
console.log(data);
$scope.Items=data;

});

$scope.showModal = false;
$scope.refresh = function(){
$http.get('http://localhost:8080/rest/twitter/' + $scope.select)
.success(function(data)
{
console.log(data);
$scope.Items=data;

}).error(function (data, status, headers, config) {
   $scope.showModal = !$scope.showModal;
console.log(status + ' ' + headers);     
}); 
}

$scope.selectedRow = null;

$scope.setClickedRow = function(index)
{
console.log(index)
$scope.selectedRow = index;  
}


$scope.RadioChange = function (s) 
{
$scope.ValueSelected = s;
$scope.row = $scope.selectedRow +1;
var endPoint ='http://localhost:8080/rest/twit/list/' + $scope.row + '/' + $scope.ValueSelected; 
console.log(endPoint)
$http.get(endPoint).success(function(data)
{ console.log(data); });  }


$scope.sentPost = function() {

$http({
url: 'http://localhost:8080/rest/twitterapi',
method: "POST",
data: JSON.stringify({"accountName":$scope.AccountName,"consumerKey":$scope.ConsumerKey,"consumerSecret":$scope.ConsumerKeySecret,"accessToken":$scope.AccessToken,"accessTokenSecret":$scope.AccessTokenSecret}),
headers: {'Content-Type': 'application/json'}
}).success(function (data, status, headers, config) {
$window.location.reload();
console.log(data); // assign  $scope.persons here as promise is resolved here 
}).error(function (data, status, headers, config) {
console.log(status + ' ' + headers);
});

} 


$scope.updatePost = function() {

$http({
url: 'http://localhost:8080/rest/update',
method: "POST",
data: JSON.stringify({"accountName":$scope.select,"consumerKey":$scope.ConsumerKeyupdate,"consumerSecret":$scope.ConsumerSecretupdate,"accessToken":$scope.AccessTokenupdate,"accessTokenSecret":$scope.AccessTokenSecretupdate}),
headers: {'Content-Type': 'application/json'}
}).success(function (data, status, headers, config) {
console.log(data); // assign  $scope.persons here as promise is resolved here 
}).error(function (data, status, headers, config) {
console.log(status + ' ' + headers);
});

} 


$http.get('http://localhost:8080/rest/api/list').success(function(data)
{ 

$scope.datapi=data;
$scope.single_object = $filter('filter')(data, function (d) {return d.accountName;})[0];
console.log($scope.single_object);
});

$scope.select=null;


$scope.selectname = function(name)
{

$scope.select = name;  
console.log(name);
}
}]);




App.directive('modal', function () {
    return {
      template: '<div class="modal fade">' + 
          '<div class="modal-dialog">' + 
            '<div class="modal-content">' + 
              '<div class="modal-header">' + 
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' + 
                '<h4 class="modal-title">{{ title }}</h4>' + 
              '</div>' + 
              '<div class="modal-body" ng-transclude></div>' + 
            '</div>' + 
          '</div>' + 
        '</div>',
      restrict: 'E',
      transclude: true,
      replace:true,
      scope:true,
      link: function postLink(scope, element, attrs) {
        scope.title = attrs.title;

        scope.$watch(attrs.visible, function(value){
          if(value == true)
            $(element).modal('show');
          else
            $(element).modal('hide');
        });

        $(element).on('shown.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = true;
          });
        });

        $(element).on('hidden.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = false;
          });
        });
      }
    };
  });
