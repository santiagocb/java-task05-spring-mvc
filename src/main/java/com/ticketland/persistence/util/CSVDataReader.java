package com.ticketland.persistence.util;

import java.util.List;

public interface CSVDataReader<T> {

    List<T> getDataFromCSV(String filePath);
}
