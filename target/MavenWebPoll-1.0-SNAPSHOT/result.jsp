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
        <form action="AnswerServlet" class="webPoll">
            <h4>Er zijn allerlei manieren waarop uw browsergedrag wordt vastgelegd, bij zowel de provider als bij de websites. Maakt u zich daar zorgen over?</h4> 
            <h3>                    In totaal hebben er   ${TOTALVOTES} mensen gestemd!</h3>
            <fieldset>
                <ul>
                    <table border="0">
                        <tbody>
                            <c:forEach var="tempAnswer" items="${ANSWER_LIST}">
                                <%-- set variable class for label --%> 
                                <c:choose>
                                    <c:when test="${tempAnswer.id == ID}">
                                        <c:set var="varclass" value="poll_checkedactive" />   
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="varclass" value="poll_active" />   
                                    </c:otherwise>
                                </c:choose>                                  
                                <tr> 
                                    <td>
                                        <label class="${varclass}">
                                            ${tempAnswer.total}% 
                                        </label>
                                    </td>
                                    <td>
                                        <label class="${varclass}">
                                            ${tempAnswer.question}  
                                        </label>
                                    </td>
                                </tr>
                            </c:forEach> 
                        </tbody>
                    </table>
                </ul>
            </fieldset>
        </form>
    </body>
</html>
