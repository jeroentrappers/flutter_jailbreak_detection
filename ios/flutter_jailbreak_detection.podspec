#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint flutter_jailbreak_detection.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'flutter_jailbreak_detection'
  s.version          = '1.0.0'
  s.summary          = 'Flutter jailbreak and root detection plugin.'
  s.description      = <<-DESC
Flutter jailbreak and root detection plugin. This plugin wraps IOSSecuritySuite for use on iOS.
                       DESC
  s.homepage         = 'https://github.com/jeroentrappers/flutter_jailbreak_detection'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Jeroen Trappers' => 'jeroen@appmire.be' }
  s.source           = { :path => '.' }
  s.source_files = 'flutter_jailbreak_detection/Sources/flutter_jailbreak_detection/**/*'
  s.dependency 'Flutter'
  s.dependency 'IOSSecuritySuite', '~> 1.9.11'
  s.platform = :ios, '12.0'

  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
  s.swift_version = '5.0'
end
