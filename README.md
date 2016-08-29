# KIE Aggregator

KIE Aggregator is a library for social media filtering about a specific subject.

It supports:
- [x] Tweets
- [x] RSS posts
- [x] Google Plus posts
- [ ] YouTube videos
- [x] Vimeo videos

## Pre-requisites
- Java 8
- [Maven 3](https://github.com/droolsjbpm/droolsjbpm-build-bootstrap/blob/master/README.md#building-with-maven)

## Setup

#### 1. Generate a Google+ private key
- Open the [list of credentials](https://console.cloud.google.com/projectselector/apis/credentials) in the Google Cloud Platform Console;
- Select your project;
- Click "Create credentials";
- Select "Service account key";
- Click the drop-down box below Service account, then click "App Engine default service account";
- Enter a name for the service account in Name;
- Use the default Service account ID or generate a different one;
- Select the Key type as P12;
- Click "Create";
- The P12 file will be downloaded;
- Replace the "googleplus.p12" file by this one;
- Click "Library";
- Click "YouTube Data API";
- Click "Enable";
- Click "Library";
- Click "Google+ API";
- Click "Enable";
- Open [Service Account Manager](https://console.cloud.google.com/iam-admin/serviceaccounts) to get the Service account ID;
- Open [Settings](https://console.cloud.google.com/iam-admin/settings) to get the Project ID;
- Update the "googleplus.properties" file.

#### 2. Generate a Twitter credentials
- Open the [list of apps](https://apps.twitter.com/) in the Twitter Application Management;
- Click "Create New App";
- Insert the "Name", the "Description" and the "Website";
- Check the checkbox "Yes, I have read and agree to the Twitter Developer Agreement";
- Click "Create your Twitter application";
- Click "Keys and Access Tokens";
- Update the "twitter4j.properties" file.

#### 3. Build
- `mvn clean install`

## Demo
See it in action:
![Demo](https://raw.githubusercontent.com/karreiro/kie-aggregator/master/assets/sample.gif).

## Contributing
- Fork it
- Create your feature branch (`git checkout -b my-new-feature`)
- Commit your changes (`git commit -am 'Add some feature'`)
- Push to the branch (`git push origin my-new-feature`)
- Create new Pull Request

## License

KIE Aggregator is released under [Apache 2 License](https://github.com/karreiro/kie-aggregator/blob/master/LICENSE-ASL-2.0.txt).
