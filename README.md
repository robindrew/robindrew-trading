# robindrew-trading
Algorithmic trading core library for Java

## Sub Projects
There are a number of provider implementations and PCF data projects available, built on this library.

Sub Project | Description
------------ | -------------
[robindrew-trading-igindex](https://github.com/robindrew/robindrew-trading-igindex) | IG Index Provider
[robindrew-trading-igindex-feed](https://github.com/robindrew/robindrew-trading-igindex-feed) | IG Index Feed Website
[robindrew-trading-igindex-trader](https://github.com/robindrew/robindrew-trading-igindex-trader) | IG Index Trader Website
[robindrew-trading-fxcm](https://github.com/robindrew/robindrew-trading-fxcm) | FXCM Provider
[robindrew-trading-fxcm-data](https://github.com/robindrew/robindrew-trading-fxcm-data) | FXCM PCF Data
[robindrew-trading-activetick](https://github.com/robindrew/robindrew-trading-activetick) | ActiveTick Provider
[robindrew-trading-activetick-feed](https://github.com/robindrew/robindrew-trading-activetick-feed) | ActiveTick Feed Website
[robindrew-trading-activetick-data](https://github.com/robindrew/robindrew-trading-activetick-data) | ActiveTick PCF Data
[robindrew-trading-backtest](https://github.com/robindrew/robindrew-trading-backtest) | Backtest Provider
[robindrew-trading-backtest-executor](https://github.com/robindrew/robindrew-trading-backtest-executor) | Backtest Executor Website
[robindrew-trading-histdata-data](https://github.com/robindrew/robindrew-trading-histdata-data) | HistData PCF Data

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
* [IG Index Trader](https://github.com/robindrew/robindrew-trading/wiki/Service:-IG-Index-Trader)

