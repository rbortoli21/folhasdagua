$(document).ready(function() {
  // remove classes from all
  $(".carousel-item").removeClass("active");
  $(".carousel-item:first").addClass("active");
  $("#loader").hide();
});
