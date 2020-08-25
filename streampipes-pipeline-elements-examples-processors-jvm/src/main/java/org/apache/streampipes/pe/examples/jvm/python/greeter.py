#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
from streampipes.core import StandaloneSubmitter, EventProcessor
from streampipes.manager import Declarer


class Greeter(EventProcessor):
    greeting = None

    def on_invocation(self):
        # extract greeting text from static property
        self.greeting = self.static_properties.get('greeting')

    def on_event(self, event):
        event['greeting'] = self.greeting
        return event

    def on_detach(self):
        pass


def main():
    # dict with processor id and processor class
    processors = {
        'org.apache.streampipes.examples.python.processor.greeter': Greeter,
    }

    Declarer.add(processors=processors)
    StandaloneSubmitter.init()


if __name__ == '__main__':
    main()