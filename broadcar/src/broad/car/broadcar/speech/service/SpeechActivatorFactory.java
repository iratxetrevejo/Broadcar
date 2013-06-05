/*
 * Copyright 2012 Greg Milette and Adam Stroud
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package broad.car.broadcar.speech.service;


import android.content.Context;

/**
 * help create {@link SpeechActivator}s based on a type from the resources
 * @author Greg Milette &#60;<a href="mailto:gregorym@gmail.com">gregorym@gmail.com</a>&#62;
 */
public class SpeechActivatorFactory
{
    public static SpeechActivator createSpeechActivator(Context context, 
            SpeechActivationListener callback, String type)
    {
        SpeechActivator speechActivator = null;
            speechActivator = new WordActivator(context, callback, type);
            return speechActivator;
    }

}
