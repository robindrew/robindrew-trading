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
[robindrew-trading-fxcm-data](https://github.com/robindrew/robindrew-trading-fxcm-data) | FXCM | PCF Data
[robindrew-trading-activetick](https://github.com/robindrew/robindrew-trading-activetick) | ActiveTick | Provider
[robindrew-trading-activetick-feed](https://github.com/robindrew/robindrew-trading-activetick-feed) | ActiveTick Feed | Service
[robindrew-trading-activetick-data](https://github.com/robindrew/robindrew-trading-activetick-data) | ActiveTick | PCF Data
[robindrew-trading-backtest](https://github.com/robindrew/robindrew-trading-backtest) | Backtest | Provider
[robindrew-trading-backtest-executor](https://github.com/robindrew/robindrew-trading-backtest-executor) | Backtest Executor | Service
[robindrew-trading-histdata-data](https://github.com/robindrew/robindrew-trading-histdata-data) | HistData | PCF Data

## Articles
Before you dive in to the API itself, please take a quick look at the following articles:
* [Introduction to Trading](https://github.com/robindrew/robindrew-trading/wiki/Article:-Introduction-to-Trading)
* [What is Your Trading Style?](https://github.com/robindrew/robindrew-trading/wiki/Article:-What-is-Your-Trading-Style%3F)

## Coding
Please read the [Wiki](https://github.com/robindrew/robindrew-trading/wiki) to get started writing code.
There is also [Javadoc](https://htmlpreview.github.io/?https://raw.githubusercontent.com/robindrew/robindrew-javadoc/master/docs/index.html?overview-summary.html) available for all my projects.

## Dependencies
This library requires the [robindrew-common](https://github.com/robindrew/robindrew-common) project.

## Data
PCF formatted data for the major FX pairs is available:
* [HistData PCF Files](https://github.com/robindrew/robindrew-trading-histdata-data)
* [ActiveTick PCF Files](https://github.com/robindrew/robindrew-trading-activetick-data)
* [FXCM PCF Files](https://github.com/robindrew/robindrew-trading-fxcm-data)

## Providers
A provider is an implementation of the ITradingPlatform along with supporting classes to simply access to an existing broker APIs.
The following providers are available, including a provider specifically for backtesting against historic data:
* [IG Index Provider](https://github.com/robindrew/robindrew-trading-igindex)
* [Active Tick Provider](https://github.com/robindrew/robindrew-trading-activetick)
* [Backtest Provider](https://github.com/robindrew/robindrew-trading-backtest)

Note: IG Index does **not** provide access to any useful amount of historic price data.

## Services
A service is a stand-alone web application providing a front end to various trading functionality.

#### Streaming Prices
The following projects provide access to the live data feed for the given provider. Each includes asynchronous file output, writing the individual price ticks in a text format. In addition the feeds provide a web interface to view and manage the price feeds.
* [IG Index Feed](https://github.com/robindrew/robindrew-trading-igindex-feed)
* [Active Tick Feed](https://github.com/robindrew/robindrew-trading-activetick-feed)

#### Live Trading
The following projects provide trading access for the given provider, including position management, history and execution. Currently there is only one trading service:
* [IG Index Trader](https://github.com/robindrew/robindrew-trading/wiki/Service:-IG-Index-Trader)

#### Backtesting
The following project provides a web interface to the backtesting provider, along with visualisation for historic prices in PCF format:
* [Backtest Executor](https://github.com/robindrew/robindrew-trading-backtest-executor)
