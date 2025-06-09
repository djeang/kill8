# kill8

A cross-platform command-line utility to kill processes running on specific ports. Perfect for developers who need to quickly free up ports during development.

## What it does

**kill8** finds and terminates processes that are listening on a specified port. I
t works across different operating systems (macOS, Linux, and Windows) using the appropriate system commands for each platform.

# Install

```shell
jeka app: install repo=djeang/kill8
```

Or install the native version:
```shell
jeka app: install repo=djeang/kill8 native:
```

## Usage

### Basic Usage

Kill a process running on port 8080 (default):
```shell
kill8
```

Kill a process running on a specific port:
```shell
kill8 <port-number>
```

Example:
```shell
kill8 3000
```