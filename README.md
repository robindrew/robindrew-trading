# robindrew-trading
Algorithmic trading core library for Java

## Sub Projects
There are a number of provider implementations and services built on this library. There are also a number of projects containing PCF data for use with backtesting.

Sub Project | Description | Type
------------ | ------------- | -------------
[robindrew-trading-igindex](https://github.com/robindrew/robindrew-trading-igindex) | IG Index | Provider 
[robindrew-trading-igindex-feed](https://github.com/robindrew/robindrew-trading-igindex-feed) | IG Index Feed | Service
[robindrew-trading-igindex-trader](https://github.com/robindrew/robindrew-trading-igindex-trader) | IG Index Trader | Service
[robindrew-trading-fxcm](https://github.com/robindrew/robindrew-trading-fxcm) | FXCM | Provider
[robindrew-trading-fxcm-trader](https://github.com/robindrew/robindrew-trading-fxcm-trader) | FXCM Trader | Service
[robindrew-trading-fxcm-data](https://github.com/robindrew/robindrew-trading-fxcm-data) | FXCM | PCF Data
[robindrew-trading-backtest](https://github.com/robindrew/robindrew-trading-backtest) | Backtest | Provider
[robindrew-trading-backtest-executor](https://github.com/robindrew/robindrew-trading-backtest-executor) | Backtest Executor | Service
[robindrew-trading-histdata](https://github.com/robindrew/robindrew-trading-histdata) | HistData | Provider
[robindrew-trading-histdata-data](https://github.com/robindrew/robindrew-trading-histdata-data) | HistData | PCF Data
[robindrew-trading-oanda](https://github.com/robindrew/robindrew-trading-oanda) | OANDA | Provider
[robindrew-trading-oanda-trader](https://github.com/robindrew/robindrew-trading-oanda-trader) | OANDA | Service
[robindrew-trading-cityindex](https://github.com/robindrew/robindrew-trading-cityindex) | City Index | Provider

Note: I no longer actively maintain the ActiveTick projects, if someone else is interested in accessing them, please contact me directly.

## Articles
Before you dive in to the API itself, please take a quick look at the following articles:
* [Introduction to Trading](https://github.com/robindrew/robindrew-trading/wiki/Article:-Introduction-to-Trading)
* [What is Your Trading Style?](https://github.com/robindrew/robindrew-trading/wiki/Article:-What-is-Your-Trading-Style%3F)

## Coding
Please read the [Wiki](https://github.com/robindrew/robindrew-trading/wiki) to get started writing code.
There is also [Javadoc](https://htmlpreview.github.io/?https://raw.githubusercontent.com/robindrew/robindrew-javadoc/master/docs/index.html?overview-summary.html) available for all my projects.

## Dependencies
This library requires the [robindrew-common](https://github.com/robindrew/robindrew-common) project.

## Historic Price Data
PCF formatted data for the major FX pairs is available:
* [HistData PCF Files](https://github.com/robindrew/robindrew-trading-histdata-data)
* [FXCM PCF Files](https://github.com/robindrew/robindrew-trading-fxcm-data)

Note: IG Index does **not** provide access to any useful quantity of historic price data. Strict limits exist on the amount of historic price data that can be queried via the REST API each month.

Note: The ActiveTick PCF files have been archived and should not be used. It is in the old format and as I have cancelled access to ActiveTick I do not have the ability to update them.

## Providers
A provider is an implementation of the trading platform along with supporting classes to simplify access to existing broker APIs.
The following providers are available, including a provider specifically for backtesting against historic data:
* [IG Index Provider](https://github.com/robindrew/robindrew-trading-igindex)
* [FXCM Provider](https://github.com/robindrew/robindrew-trading-fxcm)
* [OANDA Provider](https://github.com/robindrew/robindrew-trading-oanda)
* [Backtest Provider](https://github.com/robindrew/robindrew-trading-backtest)


## Services
A service is a stand-alone web application providing a front end to various trading functionality.

#### Streaming Prices
The following project provides access to the live data feed for all available providers. Each includes asynchronous file output, writing the individual price ticks in a text format. In addition the feeds provide a web interface to view and manage the price feeds.
* [Multi Feed Service](https://github.com/robindrew/robindrew-trading-multifeed)

#### Live Trading
The following projects provide trading access for the given provider, including position management, history and execution:
* [IG Index Trader Service](https://github.com/robindrew/robindrew-trading-igindex-trader)
* [FXCM Trader Service](https://github.com/robindrew/robindrew-trading-fxcm-trader)

#### Backtesting
The following project provides a web interface to the backtesting provider, along with visualisation for historic prices in PCF format:
* [Backtest Executor Service](https://github.com/robindrew/robindrew-trading-backtest-executor)
