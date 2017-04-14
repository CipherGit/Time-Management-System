var groupMembers = [];
var allEvents = [];
var minTime = "05:00:00"
var maxTime = "24:00:00"

//Retrieve Data from Servlet via AJAX
function retrieveData() {
  $.ajax({
    type: 'POST',
    url: 'GroupSchedulerServlet',
    dataType: 'json',
    success: function(data) {
      //Clear global arrays
      allEvents.length = 0;
      groupMembers.length = 0;

      //Fill them with new information
      for(var i = 0; i<data.length; i++){
        var groupMember = {
          name: data[i].name,
          username: data[i].username,
          schedule: data[i].schedule.split("")
        }
        groupMembers.push(groupMember);
        events = data[i].events
        for(var j = 0; j<events.length; j++){
          var eventItem = {
            title: events[j].title,
            start: events[j].start,
            end: events[j].end,
            rendering: 'background',
            backgroundColor: "rgba(0, 255, 0, " + 1/data.length + ")"
          }
          allEvents.push(eventItem);
        }
      }
      $('#calendar').fullCalendar('removeEvents');
      $('#calendar').fullCalendar('addEventSource', allEvents);
      $('#calendar').fullCalendar('rerenderEvents');
    }
  });
}
$(document).ready(function() {
    $('#calendar').fullCalendar({
        minTime: minTime,
        maxTime: maxTime,
        defaultDate: "2017-04-05",
        visibleRange: {
          start: '2017-04-02',
          end: '2017-04-08'
        },
        defaultView: 'agendaWeek',
        columnFormat: 'dddd',
        header: false,
        allDaySlot: false,
        editable: false,
        events: [],
        dayClick: function(date, jsEvent, view) {
          $('#TimeSlotHeader').html(date.format('dddd, h:mma'))
          $('#TimeSlotDetail').modal({show:true})
          getIndex(date);
        }
    });
});
function getIndex(date) {
  var view = $('#calendar').fullCalendar('getView');
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
      alert(response);
    },
    data: JSON.stringify(calendarData)
  });
}
