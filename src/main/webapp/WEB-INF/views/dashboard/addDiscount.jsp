<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../include.jsp" />

    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/tcrs-generated-labels.min.js"></script>
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/toolcool-range-slider.min.js"></script>
</head>
<body>
    <h2>Add Discount</h2>
    <form action="add-discount" method="post">
        <div id="div-name">
            <label for="name">Name :</label>
            <input type="text" id="name" name="name" value="">
        </div>
        <div id="div-date">
            <label for="start-date">Start Date :</label>
            <input type="date" id="start-date" name="start-date" value="">
            <label for="end-date">End Date :</label>
            <input type="date" id="end-date" name="end-date" value="">
        </div>
        <div id="div-discount-percentage">
            <input type="hidden" id="discount-percentage" name="discount-percentage" value="50">
            <fieldset class="border p-1">
                <legend class="w-auto float-none">Discount Percentage</legend>
                <tc-range-slider
                        id="discountPercentageSlider"

                        min="0"
                        max="100"
                        value="50"
                        step="1"
                        round="0"

                        generate-labels="true"
                        generate-labels-units="%"
                ></tc-range-slider>
            </fieldset>
        </div>
        <button type="submit">
            Confirm
        </button>
    </form>
    <script>
        const discountPercentageSlider = document.getElementById('discountPercentageSlider');
        const discountPercentage = document.getElementById('discount-percentage');
        discountPercentageSlider.addEventListener('change', (evt) => {
            discountPercentage.setAttribute("value", evt.detail.value);
        });
    </script>
</body>
</html>
