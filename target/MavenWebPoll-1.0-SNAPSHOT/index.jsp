<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="CSS/app.css">        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select </title>
    </head>
    <body>
        <br>
        <form action="AnswerServlet" class="webPoll" method="post">
            <h4>Er zijn allerlei manieren waarop uw browsergedrag wordt vastgelegd, bij zowel de provider als bij de websites. Maakt u zich daar zorgen over?</h4> 

            <fieldset>
                <ul>
                    <c:forEach var="tempAnswer" items="${ANSWER_LIST}">
                        <%-- set variable to checked if id=1 so the first radio button is always selected --%> 
                        <c:set var="checked" value=""/>                        
                        <c:if test="${tempAnswer.id == 1}">
                            <c:set var="checked" value="checked"/>
                        </c:if>                        
                        <li> 
                            <label class="poll_active">
                                <input type="radio" name="Vote" value="${tempAnswer.id}" ${checked} >${tempAnswer.question} <%= request.getParameter("tempAnswer.question")%></label>
                        </li>
                    </c:forEach>                    
                </ul>
            </fieldset>
            <p class="buttons">
                <input class="vote" type="submit" value="Stem!" name="command" />
            </p>

        </form>
    </body>
</html>
