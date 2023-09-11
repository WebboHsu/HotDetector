# HotDetector (Heated Topic Detector for Chinese Social Media)

HotDetector is a practical application that helps users automatically extract content related to specific domains (Technology, Entertainment, Sports, Custom) from Weibo's hot search, Zhihu's hot list, and Baidu's real-time hot topics.

It is developed using Java SE 8.

The developers of this application are undergraduate students from the School of Information Science and Technology at Peking University: **Weibo Xu**, **Lingran Zhao**, and **Yiping Xie**.

## Usage

Click the buttons labeled "探测科技/体育/娱乐/自定义热门" to start detecting hot topics in the selected domain.

All hot items related to the chosen domain will be displayed on the interface. Click on them to open and view in your browser.

Images related to Zhihu's hot list will be displayed on the interface. Click on them to open and view the full-sized image in your browser.

Clicking on the titles "热搜" "热榜" "实时热点" in the header will directly take you to the Weibo hot search, Zhihu hot list, and Baidu real-time hot topics pages to view all hot items.

Click the "检查词库更新" button to check if there are updates in the cloud-based word library. In fact, it automatically checks for updates every time you open the application.

Click the "管理词库" button to edit the word library.

Click the "帮助" button to get more information.

## Basic Principles

For each domain, there is a domain word library and a domain blocklist.

During the detection process, this application will automatically compare the network's hot items with the word library of the selected domain.

Items containing vocabulary from the domain word library and not containing vocabulary from the domain blocklist are considered related to that domain.

Each time you open this application, it communicates with the cloud server to check if the word library has been updated.

If there are updates, it will download the new version of the word library file.

## Source Code Structure

It is divided into three layers: **UI layer, Business Logic layer, and Data Access layer**.

### UI Layer

**MainFrame.java**: Main window

**WordBagManagerDialog.java**: Word library management window

**WordBagUpdate.java**: Word library update dialog

### Business Logic Layer

**Main.java**: Main function file

**Warning.java**: Warning messages

**Field.java**: Enumeration for domains

**CurrentField.java**: Currently selected domain

**WordBagUpdater.java**: Word library update

**HotItemsParser.java**: Determine if hot items are related to the currently selected domain

### Data Access Layer

**Detector.java**: Web crawler, reads hot items

**HotItem.java**: Encapsulates hot items

**Config.java**: Encapsulates user configuration information

**WordBagManager.java**: Manages and edits the word library
