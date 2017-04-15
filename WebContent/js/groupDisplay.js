var allData = [];
var minTime = "05:00:00"
var maxTime = "24:00:00"

//Retrieve Data from Servlet via AJAX
function retrieveData(i, groupID) {
  $.ajax({
    type: 'POST',
    url: 'GroupSchedulerServlet',
    data:{group:groupID},
    dataType: 'json',
    success: function(data) {

      var groupMembers = [];
      var allEvents = [];

      //Fill them with new information
      for(var j = 0; j<data.length; j++){
        var groupMember = {
          name: data[j].name,
          username: data[j].username,
          schedule: data[j].schedule.split("")
        }
        groupMembers.push(groupMember);
        events = data[j].events
        for(var k = 0; k<events.length; k++){
          var eventItem = {
            id: groupMember.name,
            title: events[k].title,
            start: events[k].start,
            end: events[k].end,
            rendering: 'inverse-background',
            backgroundColor: "rgba(0, 255, 0, " + 1/data.length + ")"
          }
          allEvents.push(eventItem);
        }
      }
      var dataSample = {
        gms: groupMembers,
        aes: allEvents
      }
      allData.push(dataSample);
      console.log(i);
      console.log(allData);
      $('#calendar'+i).fullCalendar('removeEvents');
      $('#calendar'+i).fullCalendar('addEventSource', allData[i].aes);
      $('#calendar'+i).fullCalendar('rerenderEvents');
    }
  });
};
function getIndex(i, date) {
  var view = $('#calendar'+i).fullCalendar('getView');
  var calendarData = {
      startDate: view.start.format(),
      endDate: view.end.format(),
      minTime: minTime,
      maxTime: maxTime,
      slot: date.format()
  }
  $.ajax({
    type:'POST',
    url:'SchedulerServlet',
    headers: { 'Purpose': 'index' },
    dataType: 'json',
    success: function (response) {
      calculateAvailability(i, response);
    },
    data: JSON.stringify(calendarData)
  });
}
function calculateAvailability(i, index){
  if(allData[i].gms.length > 0) {
    var available = [];
    var unavailable = [];
    var availabilityCount = 0;
    for(var j = 0; j<allData[i].gms.length; j++){
      var availability = parseInt(allData[i].gms[j].schedule[index]);
      if(availability === 1){
        unavailable.push(allData[i].gms[j].name);
        availabilityCount += 0;
      } else {
        available.push(allData[i].gms[j].name);
        availabilityCount += 1;
      }
    }
    modifyModal(i, availabilityCount, available, unavailable)
  }
}
function modifyModal(i, availabilityCount, available, unavailable){
  $('#availabilityCount').html("Availability Count: "+availabilityCount+"/"+allData[i].gms.length);

  // clear the existing list
  $('#availableList li').remove();
  $('#unavailableList li').remove();

  var aList = $('ul#availableList')
  $.each(available, function(j) {
      var li = $('<li/>')
      .text(available[j])
      .appendTo(aList);
  });

  var uList = $('ul#unavailableList')
  $.each(unavailable, function(j) {
      var li = $('<li/>')
      .text(unavailable[j])
      .appendTo(uList);
  });
}
