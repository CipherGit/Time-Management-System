var minTime = "05:00:00"
var maxTime = "24:00:00"

function sendJSON() {
    var view = $('#calendar').fullCalendar('getView');
    var allEvents = $('#calendar').fullCalendar('clientEvents');
    var newEvents = []
    for (var i = 0; i < allEvents.length; i++) {
        var eventItem = {
            title: allEvents[i].title,
            start: allEvents[i].start,
            end: allEvents[i].end,
        }
        newEvents.push(eventItem)
    }
    var calendarData = {
        startDate: view.start.format(),
        endDate: view.end.format(),
        minTime: minTime,
        maxTime: maxTime,
        events: newEvents
    }
    var jsonObj = JSON.stringify(calendarData);
    console.log(jsonObj)

    //Send Data to Servlet
    xhr = new XMLHttpRequest();
    var url = "SchedulerServlet";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function() {
        if (this.readyState !== 4) return; // not ready yet
        if (this.status === 200) { // HTTP 200 OK
            alert(this.responseText);
        }
    };
    xhr.send(jsonObj);
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
        selectable: true,
        selectHelper: true,
        select: function(start, end, allDay) {
            var title = prompt('Event Title:');
            if (title) {
                $('#calendar').fullCalendar('renderEvent', {
                        title: title,
                        start: start,
                        end: end,
                        allDay: false
                    },
                    true // make the event "stick"
                );
            }
            $('#calendar').fullCalendar('unselect');
        },
        editable: true,
        events: {
            url: 'SchedulerServlet',
            type: 'POST',
            error: function() {
                alert('There was an error encountered while fetching data!');
            }
        },
        eventRender: function(event, element) {
            element.bind('dblclick', function() {
                if (confirm("Delete the event: " + event.title + "?")) {
                    console.log("Deleting")
                    $('#calendar').fullCalendar('removeEvents', event._id)
                    $('#calendar').fullCalendar('rerenderEvents')
                } else {
                    //Do Nothing
                }
                console.log('Double Clicked!')
            });
        }
    });
});
