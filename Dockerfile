#
# Copyright 2015-present Sudo.ai
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

FROM debian:sid

RUN apt-get update && apt-get install -y git ant default-jdk gcc g++ zip
RUN git clone --depth 1 https://github.com/facebook/buck.git buck
COPY . weslang
WORKDIR /buck
RUN ant && ln -s $PWD/bin/buck /bin/buck
WORKDIR /weslang
RUN buck build //java/com/deezer/research/language:detection_app
EXPOSE 8080
CMD ["java", "-jar", "buck-out/gen/java/com/deezer/research/language/detection_app.jar"]
