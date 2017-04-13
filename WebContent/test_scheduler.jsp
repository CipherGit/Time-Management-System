<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.3.1/fullcalendar.min.css'/>
<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.3.1/fullcalendar.print.css' media='print'/>
<link rel='stylesheet' href='//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css'>
<style>
    body {
        margin: 40px 10px;
        padding: 0;
        font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
        font-size: 14px;
    }
    #calendar {
        max-width: 900px;
        margin: 0 auto;
    }
</style>
<title>Weekly Schedule</title>
</head>
<body>

<h1>Weekly Schedule</h1>
<p>You can insert your weekly schedule in the calendar below. Click and drag on
a time slot in order to add an event. You may drag or resize events to modify
their dates and time. If you've made a mistake, you can double click on an
event to delete it.</p>
<br>
<div id='calendar'></div>
<button type="button" onclick="sendJSON()">Save Schedule</button>

<script type="text/javascript" src='//code.jquery.com/jquery-3.2.1.min.js'></script>
<script type="text/javascript" src='//code.jquery.com/ui/1.12.1/jquery-ui.min.js'></script>
<script type="text/javascript" src='//cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js'></script>
<script type="text/javascript" src='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.3.1/fullcalendar.min.js'></script>
<script type="text/javascript" src='js/scheduler.js'></script>
</body>
</html>
