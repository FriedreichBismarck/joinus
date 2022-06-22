package com.tdsast.dao.exceptions

class ElementNotFoundException(element: String) : Exception("$element 不存在")
