package com.example.currencylistapp

import com.example.currencylistapp.data.ValCurs
import com.example.currencylistapp.data.Valute
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal fun parseXml(xmlString: String): ValCurs {
    val factory = XmlPullParserFactory.newInstance()
    val parser = factory.newPullParser()
    parser.setInput(StringReader(xmlString))

    var eventType = parser.eventType
    var date = ""
    var name = ""
    val valutes = mutableListOf<Valute>()

    var id = ""
    var numCode = ""
    var charCode = ""
    var nominal = ""
    var valuteName = ""
    var value = ""
    var vunitRate = ""

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                when (parser.name) {
                    "ValCurs" -> {
                        date = parser.getAttributeValue(null, "Date") ?: ""
                        name = parser.getAttributeValue(null, "name") ?: ""
                    }
                    "Valute" -> {
                        id = parser.getAttributeValue(null, "ID") ?: ""
                    }
                    "NumCode" -> numCode = parser.nextText()
                    "CharCode" -> charCode = parser.nextText()
                    "Nominal" -> nominal = parser.nextText()
                    "Name" -> valuteName = parser.nextText()
                    "Value" -> value = parser.nextText().replace(",", ".")
                    "VunitRate" -> vunitRate = parser.nextText().replace(",", ".")
                }
            }

            XmlPullParser.END_TAG -> {
                if (parser.name == "Valute") {
                    valutes.add(Valute(numCode, charCode, nominal, valuteName, value, vunitRate, id))
                }
            }
        }
        eventType = parser.next()
    }

    return ValCurs(valutes)
}

fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}
