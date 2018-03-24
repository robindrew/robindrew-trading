# robindrew-trading
Algorithmic trading core library for Java

## Articles
Before you dive in to the API itself, please take a quick look at the following articles:
* [Introduction to Trading](https://github.com/robindrew/robindrew-trading/wiki/Article:-Introduction-to-Trading)
* [My Approach to Trading](https://github.com/robindrew/robindrew-trading/wiki/Article:-My-Approach-to-Trading)

## Coding
Please read the [Wiki](https://github.com/robindrew/robindrew-trading/wiki) to get started writing code.
There is also [Javadoc](https://htmlpreview.github.io/?https://raw.githubusercontent.com/robindrew/robindrew-javadoc/master/docs/index.html?overview-summary.html) available for all my projects.

## Dependencies
This library requires the [robindrew-common](https://github.com/robindrew/robindrew-common) project.

## Data
PCF formatted data for the major FX pairs is available:
* [HistData PCF Files](https://github.com/robindrew/robindrew-trading-histdata-data)
* [ActiveTick PCF Files](https://github.com/robindrew/robindrew-trading-activetick-data)

## Providers
The following providers are available for the API:
* [IG Index Provider](https://github.com/robindrew/robindrew-trading-igindex)
* [Active Tick Provider](https://github.com/robindrew/robindrew-trading-activetick)

## Services
The following projects provide access to the live data feed for the given provider. Each includes asynchronous file output, writing the individual price ticks in a text format. In addition the feeds provide a web interface to view and manage the price feeds.
* [IG Index Feed](https://github.com/robindrew/robindrew-trading-igindex-feed)
* [Active Tick Feed](https://github.com/robindrew/robindrew-trading-activetick-feed)

The following projects provide trading access for the given provider, including position management, history and execution. Currently there is only one provider:
* [IG Index Trader](https://github.com/robindrew/robindrew-trading-igindex-trader)

