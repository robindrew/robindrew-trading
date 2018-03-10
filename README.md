# robindrew-trading
Algorithmic trading core library for Java

## Price Candles

Price candles are represented by the **IPriceCandle** class. A price candle consists of:

* Open Price
* High Price
* Low Price
* Close Price
* Open Date/Time
* Close Date/Time

In the API, all prices are represented as whole numbers. This allows for significan optimization of memory when serializing and persisting the data. It does however mean that a simple
transform is required to convert any given price to its corresponding **double** or **BigDecimal** representation where necessary.

There are a lot of classes included in the API for manipulating price candles.

### Price Candle Flow

I have provided both push and pull mechanisms for reading and writing price candles for maximum flexibility.

* **IPriceCandleStreamSink** - event driven interface for handling individual candles
* **IPriceCandleStreamSource** - event driven interface for retrieving individual candles
* **IPriceCandleListSink** - event driven interface for handling a batch of candles
* **IPriceCandleListSource** - event driven interface for retrieving a batch of candles

### Reading Candles from Files

The **PriceCandleDirectoryStreamSource** is the primary method for reading candles from one or more text files.
It is very common to receive historic data in an Excel friendly CSV one-candle-per-line format.
The source requires that a parser is supplied to transform each line in the file to a candle.

     // Create the line parser
     String directory = "c:\\data\\EURUSD.txt";
     IPriceCandleLineParser parser = new PriceCandleLineParser();
     ILineFilter filter = new DeclineSetFilter();
     IPriceCandStreamSource source = new PriceCandleDirectoryStreamSource(directory, parser, filter);


### Filtering & Transforming

The **PriceCandleIntervalStreamSource** allows you to transform candles from a more fine-grained time interval to a more course one.
This is the most frequently used sink as it is used to aggregate the source tick candles in to 5 Minute or 1 Day candles for example.

The **PriceCandleFilteredStreamSource** allows you to filter out candles based on certain criteria.
This is most often used to filter within a given date range.

    // Create a source over a file or streaming price from a provider
    IPriceCandStreamSource source = ...
    
    // Wrap the source with an aggregator which produces hourly candles
    source = new PriceCandleIntervalStreamSource(source, PriceCandleIntervals.HOURLY);
    
    // Wrap the source with a filter to produce only candles for the year 2017
    LocalDate from = LocalDate.of(2017, 01, 01);
    LocalDate to = LocalDate.of(2017, 12, 31);
    source = new PriceCandleFilteredStreamSource(source, new PriceCandleDateFilter(from, to));
    
    // Wrap the source to log every 100th candle (so we can see progress in the log file)
    source = new PriceCandleLoggedStreamSource(source, 100);


### Processing Candles

The **IPriceCandleStreamSink** is an event driven interface to handle price candles.
This can be anything from a file to which the candles are to be written, to an implementation of **ITradingStrategy** that is to be applied to the candles. 

For live price handling, this interface will be driven directly by the price stream implementation for your provider. See the foot of this page for available providers.

For backtesting, this interface will be driven from a **IPriceCandStreamSource** streaming historic candles from local files or a database.


    // For backtesting we firstly create a source of candles from historic data files 
    IPriceCandStreamSource source = ...
    
    // Create the strategy we want to apply to the historic data
    ITradingStrategy sink = ...
    
    // Pipe the historic candles in to our trading strategy and see what happens!
    new PriceCandleStreamPipe(source, sink).pipe();



## Providers

* [robindrew-trading-igindex](https://github.com/robindrew/robindrew-trading-igindex) - IG Index Provider
