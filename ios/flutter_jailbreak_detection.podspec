#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'flutter_jailbreak_detection'
  s.version          = '0.0.1'
  s.summary          = 'Flutter jailbreak and root detection plugin'
  s.description      = <<-DESC
Flutter jailbreak and root detection plugin
                       DESC
  s.homepage         = 'https://github.com/jeroentrappers/flutter_jailbreak_detection'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Jeroen Trappers' => 'jeroen@appmire.be' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.dependency 'DTTJailbreakDetection'

  s.ios.deployment_target = '8.0'
end

